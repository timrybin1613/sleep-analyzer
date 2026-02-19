package ru.ya.practicum;

import java.util.List;
import java.util.function.Function;

public class BadQualitySleepSessionsCountFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepingSessions) {
        return new SleepAnalysisResult<>(
                sleepingSessions.stream()
                        .filter(sleepingSession -> sleepingSession.getSleepQuality().equals(SleepQuality.BAD)).count(),
                "Функция подсчета сессий с плохим качеством сна"
        );
    }
}
