package ru.ya.practicum;

import java.io.*;
import java.time.Duration;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;

public class SleepTrackerApp {

    private static final List<Function<List<SleepingSession>, ? extends SleepAnalysisResult<?>>> FUNCTIONS = List.of(
            new SleepSessionsCountFunction(),
            new AverageSleepDurationFunction(),
            new BadQualitySleepSessionsCountFunction(),
            new MaxSleepDurationFunction(),
            new MinSleepDurationFunction(),
            new SleeplessNightsCountFunction(),
            new ChronotypeCalculationFunction());
    private static final Logger log = Logger.getLogger(SleepTrackerApp.class.getName());

    private static void printResult(SleepAnalysisResult<?> result) {
        System.out.println(result.getDescription() + ": " + format(result.getResult()));
    }

    private static String format(Object result) {
        if (result instanceof Duration) {
            return ((Duration) result).toMinutes() + " минут.";
        }
        if (result instanceof Chronotype) {
            return "Хронотип - " + ((Chronotype) result).getTitle();
        }
        return result.toString();
    }

    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("Не был передан файл лога для обработки");
            return;
        }
        String fileName = args[0];

        SleepDataExtractor sleepDataExtractor = new SleepDataExtractor();
        SleepingSessionParser sleepingSessionParser = new SleepingSessionParser();

        try {
            List<String> rawLog = SleepLogReader.readSleepLog(fileName);
            List<String[]> parsedLines = sleepDataExtractor.apply(rawLog);
            List<SleepingSession> sessions = sleepingSessionParser.apply(parsedLines);

            if (!sessions.isEmpty()) {
                FUNCTIONS.stream()
                        .map(function -> function.apply(sessions))
                        .forEach(SleepTrackerApp::printResult);
            } else {
                System.out.println("Не найдено ни одной сессии сна");
            }
        } catch (IOException e) {
            log.severe("Ошибка обработки файла " + fileName + ": " + e.getMessage());
        }
    }
}
