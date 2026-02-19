package ru.ya.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SleepSessionsCountFunctionTest {
    private SleepSessionsCountFunction function;

    @BeforeEach
    public void setup() {
        function = new SleepSessionsCountFunction();
    }

    @Test
    void shouldReturnOneWhenSingleSleepSessionProvided() {

        LocalDateTime startTime = LocalDateTime.of(2024, 1, 1, 0, 0);
        LocalDateTime endTime = startTime.plusSeconds(11);

        List<SleepingSession> sleepingSessions = new ArrayList<>();
        sleepingSessions.add(new SleepingSession(startTime, endTime, SleepQuality.GOOD));

        SleepAnalysisResult<Long> expectedResult = new SleepAnalysisResult<>(1L,
                "Функция по подсчету общего количества сессий сна");
        SleepAnalysisResult<Long> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnCorrectCountWhenMultipleSleepSessionsProvided() {

        LocalDateTime startTime = LocalDateTime.of(2024, 1, 1, 1, 0);
        LocalDateTime endTime = startTime.plusSeconds(11);
        LocalDateTime startTime1 = LocalDateTime.of(2024, 1, 1, 2, 0);
        LocalDateTime endTime1 = startTime1.plusSeconds(11);
        LocalDateTime startTime2 = LocalDateTime.of(2024, 1, 1, 3, 0);
        LocalDateTime endTime2 = startTime2.plusSeconds(11);

        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(startTime, endTime, SleepQuality.GOOD),
                new SleepingSession(startTime1, endTime1, SleepQuality.NORMAL),
                new SleepingSession(startTime2, endTime2, SleepQuality.BAD)
        );

        SleepAnalysisResult<Long> expectedResult = new SleepAnalysisResult<>(3L,
                "Функция по подсчету общего количества сессий сна");
        SleepAnalysisResult<Long> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    void shouldReturnZeroWhenNoSleepSessionsProvided() {
        SleepSessionsCountFunction sleepSessionsCountFunction = new SleepSessionsCountFunction();
        List<SleepingSession> sleepingSessions = new ArrayList<>();

        SleepAnalysisResult<Long> expectedResult = new SleepAnalysisResult<>(0L,
                "Функция по подсчету общего количества сессий сна");
        SleepAnalysisResult<Long> actualResult = sleepSessionsCountFunction.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }
}
