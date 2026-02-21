package ru.ya.practicum;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.LocalTime.MIDNIGHT;

public class ChronotypeCalculationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Chronotype>> {
    private static final LocalTime MORNING = LocalTime.of(6, 0);
    private static final LocalTime START_SLEEPING_OWL = LocalTime.of(23, 0);
    private static final LocalTime END_SLEEPING_OWL = LocalTime.of(9, 0);
    private static final LocalTime START_SLEEPING_LARK = LocalTime.of(22, 0);
    private static final LocalTime END_SLEEPING_LARK = LocalTime.of(7, 0);

    private boolean isNightSleep(SleepingSession s) {
        LocalDateTime start = s.getStartSleeping();
        LocalDateTime end = s.getEndSleeping();

        LocalDateTime nightStart = start.toLocalDate().atTime(MIDNIGHT);
        LocalDateTime nightEnd = start.toLocalDate().atTime(MORNING);

        if (start.toLocalTime().isAfter(MORNING)) {
            nightStart = nightStart.plusDays(1);
            nightEnd = nightEnd.plusDays(1);
        }

        return start.isBefore(nightEnd) && end.isAfter(nightStart);
    }

    private Chronotype detectChronotype(SleepingSession s) {
        LocalTime start = s.getStartSleeping().toLocalTime();
        LocalTime end = s.getEndSleeping().toLocalTime();

        if (start.isAfter(START_SLEEPING_OWL) && end.isAfter(END_SLEEPING_OWL)) {
            return Chronotype.OWL;
        }
        if (start.isBefore(START_SLEEPING_LARK) && end.isBefore(END_SLEEPING_LARK)) {
            return Chronotype.LARK;
        }
        return Chronotype.PIGEON;
    }

    @Override
    public SleepAnalysisResult<Chronotype> apply(List<SleepingSession> sleepingSessions) {

        Map<Chronotype, List<SleepingSession>> grouped = sleepingSessions.stream()
                .filter(this::isNightSleep)
                .collect(Collectors.groupingBy(this::detectChronotype));

        long max = grouped.values().stream()
                .mapToLong(List::size)
                .max().orElse(0);

        List<Chronotype> leaders = grouped.entrySet().stream()
                .filter(e -> e.getValue().size() == max)
                .map(Map.Entry::getKey).collect(Collectors.toList());

        Chronotype result =
                leaders.size() == 1 ? leaders.get(0) : Chronotype.PIGEON;
        return new SleepAnalysisResult<>(result, "Функция определения хронотипа");
    }
}
