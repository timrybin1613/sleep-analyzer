package ru.ya.practicum;

import java.util.List;
import java.util.function.Function;

public class SleepSessionsCountFunction implements Function<List<SleepingSession>, SleepAnalysisResult<Long>> {
    @Override
    public SleepAnalysisResult<Long> apply(List<SleepingSession> sleepSessions) {
        return new SleepAnalysisResult<>(sleepSessions.stream().count(),
                "Функция по подсчету общего количества сессий сна");
    }
}
