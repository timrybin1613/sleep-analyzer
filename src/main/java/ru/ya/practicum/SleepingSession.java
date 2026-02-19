package ru.ya.practicum;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class SleepingSession {
    private LocalDateTime startSleeping;
    private LocalDateTime endSleeping;
    private SleepQuality sleepQuality;
    private Duration duration;
    private LocalDate sleepNightDate;
    private static final long OFFSET = 12L;

    public SleepingSession(LocalDateTime startSleeping, LocalDateTime endSleeping, SleepQuality sleepQuality) {
        this.startSleeping = startSleeping;
        this.sleepNightDate = startSleeping.minusHours(OFFSET).toLocalDate();
        this.endSleeping = endSleeping;
        this.sleepQuality = sleepQuality;
        this.duration = Duration.between(startSleeping, endSleeping);
    }

    public LocalDate getSleepNightDate() {
        return sleepNightDate;
    }

    public Duration getDurationSleeping() {
        return duration;
    }

    public LocalDateTime getStartSleeping() {
        return startSleeping;
    }

    public LocalDateTime getEndSleeping() {
        return endSleeping;
    }

    public SleepQuality getSleepQuality() {
        return sleepQuality;
    }
}
