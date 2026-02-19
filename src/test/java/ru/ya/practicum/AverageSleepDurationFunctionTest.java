package ru.ya.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AverageSleepDurationFunctionTest {

    private AverageSleepDurationFunction function;

    @BeforeEach
    public void setUp() {
        function = new AverageSleepDurationFunction();
    }

    @Test
    public void shouldReturnZeroDurationWhenNoSessionsProvided() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();

        SleepAnalysisResult<Duration> expectedResult = new SleepAnalysisResult<>(Duration.ZERO,
                "Функция расчета средней продолжительности сессии сна");
        SleepAnalysisResult<Duration> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnExactDurationWhenSingleSessionProvided() {
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 1, 0);
        LocalDateTime endTime = startTime.plusMinutes(10);

        List<SleepingSession> sleepingSessions = List.of(new SleepingSession(startTime, endTime, SleepQuality.GOOD));

        SleepAnalysisResult<Duration> expectedResult = new SleepAnalysisResult<>(Duration.ofMinutes(10),
                "Функция расчета средней продолжительности сессии сна");
        SleepAnalysisResult<Duration> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnAverageDurationWhenMultipleSessionsProvided() {
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 1, 0);
        LocalDateTime endTime = startTime.plusMinutes(10);
        LocalDateTime startTime1 = LocalDateTime.of(2024, 3, 1, 1, 0);
        LocalDateTime endTime1 = startTime1.plusMinutes(20);

        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(startTime, endTime, SleepQuality.GOOD),
                new SleepingSession(startTime1, endTime1, SleepQuality.GOOD)
        );

        SleepAnalysisResult<Duration> expectedResult = new SleepAnalysisResult<>(Duration.ofMinutes(15),
                "Функция расчета средней продолжительности сессии сна");
        SleepAnalysisResult<Duration> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldCalculateAverageWithNonEvenDivision() {
        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 1, 0);
        LocalDateTime endTime = startTime.plusMinutes(5);
        LocalDateTime startTime1 = LocalDateTime.of(2024, 3, 1, 1, 0);
        LocalDateTime endTime1 = startTime1.plusMinutes(6);

        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(startTime, endTime, SleepQuality.GOOD),
                new SleepingSession(startTime1, endTime1, SleepQuality.GOOD)
        );

        SleepAnalysisResult<Duration> expectedResult = new SleepAnalysisResult<>(Duration.ofMinutes(5).plusSeconds(30),
                "Функция расчета средней продолжительности сессии сна");
        SleepAnalysisResult<Duration> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }
}
