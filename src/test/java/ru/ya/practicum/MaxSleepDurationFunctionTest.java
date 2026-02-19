package ru.ya.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class MaxSleepDurationFunctionTest {
    private MaxSleepDurationFunction function;

    @BeforeEach
    public void setUp() {
        function = new MaxSleepDurationFunction();
    }

    @Test
    public void shouldReturnZeroDurationWhenNoSessionsProvided() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult<Duration> expectedResult = new SleepAnalysisResult<>(Duration.ZERO,
                "Функция поиска максимального отрезка сна");
        SleepAnalysisResult<Duration> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnExactDurationWhenSingleSessionProvided() {
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 1, 0);
        LocalDateTime endTime = startTime.plusMinutes(10);

        List<SleepingSession> sleepingSessions = List.of(new SleepingSession(startTime, endTime, SleepQuality.GOOD));

        SleepAnalysisResult<Duration> expectedResult = new SleepAnalysisResult<>(Duration.ofMinutes(10),
                "Функция поиска максимального отрезка сна");
        SleepAnalysisResult<Duration> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnMaxDurationWhenMultipleSessionsProvided() {
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 1, 0);
        LocalDateTime endTime = startTime.plusMinutes(11);
        LocalDateTime startTime1 = LocalDateTime.of(2024, 3, 1, 1, 0);
        LocalDateTime endTime1 = startTime1.plusMinutes(10).plusSeconds(59);

        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(startTime, endTime, SleepQuality.GOOD),
                new SleepingSession(startTime1, endTime1, SleepQuality.BAD)
        );

        SleepAnalysisResult<Duration> expectedResult = new SleepAnalysisResult<>(Duration.ofMinutes(11),
                "Функция поиска максимального отрезка сна");
        SleepAnalysisResult<Duration> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnDurationWhenSeveralSessionsHaveSameMaxValue() {
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 1, 0);
        LocalDateTime endTime = startTime.plusMinutes(30);
        LocalDateTime startTime1 = LocalDateTime.of(2024, 3, 1, 1, 0);
        LocalDateTime endTime1 = startTime1.plusMinutes(30);
        LocalDateTime startTime2 = LocalDateTime.of(2024, 3, 1, 1, 0);
        LocalDateTime endTime2 = startTime2.plusMinutes(10);

        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(startTime, endTime, SleepQuality.GOOD),
                new SleepingSession(startTime1, endTime1, SleepQuality.BAD),
                new SleepingSession(startTime2, endTime2, SleepQuality.GOOD)
        );

        SleepAnalysisResult<Duration> expectedResult = new SleepAnalysisResult<>(Duration.ofMinutes(30),
                "Функция поиска максимального отрезка сна");
        SleepAnalysisResult<Duration> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }
}
