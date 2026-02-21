package ru.ya.practicum;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class MaxSleepDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Duration>> {

    @Override
    public SleepAnalysisResult<Duration> apply(List<SleepingSession> sleepingSessions) {
        return new SleepAnalysisResult<>(
                sleepingSessions.stream().map(SleepingSession::getDurationSleeping)
                        .max(Duration::compareTo)
                        .orElse(Duration.ZERO), "Функция поиска максимального отрезка сна"
        );
    }
}
