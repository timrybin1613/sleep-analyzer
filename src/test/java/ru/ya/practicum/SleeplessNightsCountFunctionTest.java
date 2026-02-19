package ru.ya.practicum;

import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SleeplessNightsCountFunctionTest {

    private SleeplessNightsCountFunction function;

    @Before
    public void setUp() {
        function = new SleeplessNightsCountFunction();
    }

    @Test
    public void shouldReturnZeroWhenNoSessions() {
        List<SleepingSession> sleepingSessions = new ArrayList<>();

        SleepAnalysisResult<Long> expectedResult = new SleepAnalysisResult<>(0L,
                "Функция подсчета бессонных ночей");
        SleepAnalysisResult<Long> actualResult = function.apply(sleepingSessions);

        Assertions.assertEquals(expectedResult, actualResult);
    }

    @Test
    public void shouldReturnZeroWhenNightSessionExists() {
        LocalDateTime start = LocalDateTime.of(2024, 3, 1, 1, 59);
        LocalDateTime end = start.plusHours(2);

        List<SleepingSession> sessions = List.of(
                new SleepingSession(start, end, SleepQuality.GOOD
                ));

        SleepAnalysisResult<Long> result = function.apply(sessions);

        assertEquals(0L, result.getResult());
    }

    @Test
    public void shouldDetectSleeplessNightWhenOnlyDaySleepExists() {
        LocalDateTime start = LocalDateTime.of(2024, 3, 1, 10, 0);
        LocalDateTime end = start.plusHours(1);

        List<SleepingSession> sessions = List.of(
                new SleepingSession(start, end, SleepQuality.GOOD)
        );

        SleepAnalysisResult<Long> result = function.apply(sessions);

        assertEquals(1L, result.getResult());
    }

    @Test
    public void shouldTreatMidnightAsNight() {
        LocalDateTime start = LocalDateTime.of(2024, 3, 1, 0, 0);
        LocalDateTime end = start.plusHours(2);

        List<SleepingSession> sessions = List.of(
                new SleepingSession(start, end, SleepQuality.GOOD)
        );

        SleepAnalysisResult<Long> result = function.apply(sessions);

        assertEquals(0L, result.getResult());
    }

    @Test
    public void shouldTreatSixAMAsNotNight() {
        LocalDateTime start = LocalDateTime.of(2024, 3, 1, 6, 0);
        LocalDateTime end = start.plusHours(2);

        List<SleepingSession> sessions = List.of(
                new SleepingSession(start, end, SleepQuality.GOOD)
        );

        SleepAnalysisResult<Long> result = function.apply(sessions);

        assertEquals(1L, result.getResult());
    }

    @Test
    public void shouldTreatMorningSleepAsPreviousNight() {
        LocalDateTime start = LocalDateTime.of(2024, 3, 5, 10, 0);
        LocalDateTime end = start.plusHours(1);

        SleepingSession session = new SleepingSession(start, end, SleepQuality.GOOD);

        assertEquals(LocalDate.of(2024, 3, 4), session.getSleepNightDate());
    }
}
