package ru.ya.practicum;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

import static java.time.LocalTime.MIDNIGHT;

public class SleeplessNightsCountFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {

    private static final LocalTime MORNING = LocalTime.of(6, 0);
    private static final BiPredicate<LocalDateTime, LocalDateTime> IS_VALID_RANGE = LocalDateTime::isBefore;
    private static final BiPredicate<LocalDateTime, LocalDateTime> IS_SAME_DAY = (start, end) ->
            start.toLocalDate().isEqual(end.toLocalDate());
    private static final BiPredicate<LocalDateTime, LocalDateTime> IS_NIGHT_HOURS = (start, end) -> {
        LocalTime time = start.toLocalTime();
        return !time.isBefore(MIDNIGHT) && time.isBefore(MORNING);
    };
    private static final BiPredicate<LocalDateTime, LocalDateTime> IS_NIGHT_SAME_DAY = IS_VALID_RANGE.and(IS_SAME_DAY).and(IS_NIGHT_HOURS);
    private static final BiPredicate<LocalDateTime, LocalDateTime> IS_OVERNIGHT_SLEEPING = (start, end) ->
            IS_VALID_RANGE.test(start, end) && !IS_SAME_DAY.test(start, end);

    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {

        long result = 0;
        String description = "Функция подсчета бессонных ночей";

        if (sleepingSessions.isEmpty()) {
            return new SleepAnalysisResult<>(result, description);
        }

        LocalDate minNight = sleepingSessions.stream()
                .map(SleepingSession::getSleepNightDate)
                .min(LocalDate::compareTo)
                .get();

        LocalDate maxNight = sleepingSessions.stream()
                .map(SleepingSession::getSleepNightDate)
                .max(LocalDate::compareTo)
                .get();

        result = minNight.datesUntil(maxNight.plusDays(1)).filter(night -> sleepingSessions.stream()
                .filter(s -> IS_NIGHT_SAME_DAY.or(IS_OVERNIGHT_SLEEPING).test(s.getStartSleeping(), s.getEndSleeping()))
                .map(SleepingSession::getSleepNightDate).noneMatch(night::equals)).count();

        return new SleepAnalysisResult<>(result, description);
    }
}
