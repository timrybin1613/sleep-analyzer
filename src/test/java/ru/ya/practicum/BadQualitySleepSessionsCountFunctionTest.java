package ru.ya.practicum;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BadQualitySleepSessionsCountFunctionTest {

    private BadQualitySleepSessionsCountFunction function;

    @BeforeEach
    public void setUp() {
        function = new BadQualitySleepSessionsCountFunction();
    }

    @Test
    public void shouldReturnZeroWhenNoSessionsProvided() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();
        SleepAnalysisResult<Long> expectedResult = new SleepAnalysisResult<>(0L,
                "Функция подсчета сессий с плохим качеством сна");
        SleepAnalysisResult<Long> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnZeroWhenAllSessionsAreGood() {

        LocalDateTime startTime = LocalDateTime.of(2024, 3, 1, 1, 0);
        LocalDateTime endTime = startTime.plusSeconds(11);

        List<SleepingSession> sleepingSessions = List.of(new SleepingSession(startTime, endTime, SleepQuality.GOOD));

        SleepAnalysisResult<Long> expectedResult = new SleepAnalysisResult<>(0L,
                "Функция подсчета сессий с плохим качеством сна");
        SleepAnalysisResult<Long> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnOneWhenSingleBadSessionPresent() {

        LocalDateTime startTime = LocalDateTime.of(2024, 2, 1, 1, 0);
        LocalDateTime endTime = startTime.plusSeconds(11);

        List<SleepingSession> sleepingSessions = List.of(new SleepingSession(startTime, endTime, SleepQuality.BAD));

        SleepAnalysisResult<Long> expectedResult = new SleepAnalysisResult<>(1L,
                "Функция подсчета сессий с плохим качеством сна");
        SleepAnalysisResult<Long> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnOneWhenMultipleBadSessionsPresent() {
        LocalDateTime startTime = LocalDateTime.of(2024, 2, 1, 1, 0);
        LocalDateTime endTime = startTime.plusSeconds(11);
        LocalDateTime startTime1 = LocalDateTime.of(2024, 3, 4, 1, 0);
        LocalDateTime endTime1 = startTime1.plusSeconds(11);

        List<SleepingSession> sleepingSessions = List.of(
                new SleepingSession(startTime, endTime, SleepQuality.BAD),
                new SleepingSession(startTime1, endTime1, SleepQuality.BAD)
        );

        SleepAnalysisResult<Long> expectedResult = new SleepAnalysisResult<>(2L,
                "Функция подсчета сессий с плохим качеством сна");
        SleepAnalysisResult<Long> actualResult = function.apply(sleepingSessions);

        assertEquals(expectedResult, actualResult);
    }

}
