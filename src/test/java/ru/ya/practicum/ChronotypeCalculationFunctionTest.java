package ru.ya.practicum;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ChronotypeCalculationFunctionTest {
    private ChronotypeCalculationFunction function;

    @BeforeEach
    public void setup() {
        function = new ChronotypeCalculationFunction();
    }

    @Test
    public void shouldReturnPigeonWhenNoSessionsProvided() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult<Chronotype> expectedResult = new SleepAnalysisResult<>(Chronotype.PIGEON,
                "Функция определения хронотипа");

        SleepAnalysisResult<Chronotype> actualResult = function.apply(sleepingSessions);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnDetectedChronotypeWhenSingleNightSessionPresent() {
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 23, 30);
        LocalDateTime endTime = startTime.plusHours(11);
        List<SleepingSession> sleepingSessions = List.of(new SleepingSession(startTime, endTime, SleepQuality.GOOD));

        SleepAnalysisResult<Chronotype> expectedResult = new SleepAnalysisResult<>(Chronotype.OWL,
                "Функция определения хронотипа");
        SleepAnalysisResult<Chronotype> actualResult = function.apply(sleepingSessions);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnDominantChronotypeWhenItIsUnique() {

        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 23, 30);
        LocalDateTime endTime = startTime.plusHours(11);
        LocalDateTime startTime1 = LocalDateTime.of(2024, 3, 1, 21, 30);
        LocalDateTime endTime1 = startTime1.plusHours(6);
        LocalDateTime startTime2 = LocalDateTime.of(2024, 3, 1, 21, 30);
        LocalDateTime endTime2 = startTime2.plusHours(6);

        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(startTime, endTime, SleepQuality.GOOD),
                new SleepingSession(startTime1, endTime1, SleepQuality.BAD),
                new SleepingSession(startTime2, endTime2, SleepQuality.GOOD)
        );

        SleepAnalysisResult<Chronotype> expectedResult = new SleepAnalysisResult<>(Chronotype.LARK,
                "Функция определения хронотипа");
        SleepAnalysisResult<Chronotype> actualResult = function.apply(sleepingSessions);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnPigeonWhenSeveralChronotypesHaveSameMaxCount() {

        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 23, 30);
        LocalDateTime endTime = startTime.plusHours(11);
        LocalDateTime startTime1 = LocalDateTime.of(2024, 3, 1, 21, 30);
        LocalDateTime endTime1 = startTime1.plusHours(6);
        LocalDateTime startTime2 = LocalDateTime.of(2024, 3, 1, 23, 30);
        LocalDateTime endTime2 = startTime2.plusHours(11);
        LocalDateTime startTime3 = LocalDateTime.of(2024, 3, 1, 21, 30);
        LocalDateTime endTime3 = startTime3.plusHours(6);

        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(startTime, endTime, SleepQuality.GOOD),
                new SleepingSession(startTime1, endTime1, SleepQuality.BAD),
                new SleepingSession(startTime2, endTime2, SleepQuality.GOOD),
                new SleepingSession(startTime3, endTime3, SleepQuality.GOOD)
        );

        SleepAnalysisResult<Chronotype> expectedResult = new SleepAnalysisResult<>(Chronotype.PIGEON,
                "Функция определения хронотипа");
        SleepAnalysisResult<Chronotype> actualResult = function.apply(sleepingSessions);
        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldIgnoreDaySessionsWhenCalculatingChronotype() {

        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 12, 30);
        LocalDateTime endTime = startTime.plusHours(2);
        LocalDateTime startTime1 = LocalDateTime.of(2024, 3, 1, 10, 30);
        LocalDateTime endTime1 = startTime1.plusHours(1);

        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(startTime, endTime, SleepQuality.GOOD),
                new SleepingSession(startTime1, endTime1, SleepQuality.GOOD)
        );

        SleepAnalysisResult<Chronotype> expectedResult = new SleepAnalysisResult<>(Chronotype.PIGEON,
                "Функция определения хронотипа");
        SleepAnalysisResult<Chronotype> actualResult = function.apply(sleepingSessions);

        Assertions.assertEquals(expectedResult, actualResult);
    }
}
