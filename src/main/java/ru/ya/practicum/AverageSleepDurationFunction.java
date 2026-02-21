package ru.ya.practicum;

import java.time.Duration;
import java.util.List;
import java.util.function.Function;

public class AverageSleepDurationFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Duration>> {

    @Override
    public SleepAnalysisResult<Duration> apply(List<SleepingSession> sleepingSessions) {
        Duration averageDuration;

        if (sleepingSessions.isEmpty()) {
            averageDuration = Duration.ZERO;
        } else {
            averageDuration = (sleepingSessions.stream()
                    .map(SleepingSession::getDurationSleeping).reduce(Duration.ZERO, Duration::plus)
                    .dividedBy(sleepingSessions.size()));
        }
        return new SleepAnalysisResult<>(averageDuration, "Функция расчета средней продолжительности сессии сна");
    }
}
