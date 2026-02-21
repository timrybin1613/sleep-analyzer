package ru.ya.practicum;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepingSessionParser implements Function<List<String[]>, List<SleepingSession>> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("dd.MM.yy HH:mm");

    @Override
    public List<SleepingSession> apply(List<String[]> parts) {
        return parts.stream().filter(p -> p.length == 3)
                .map(p -> new SleepingSession(
                        LocalDateTime.parse(p[0], FORMATTER),
                        LocalDateTime.parse(p[1], FORMATTER),
                        SleepQuality.valueOf(p[2].trim().toUpperCase())))
                .filter(sleepingSession ->
                        sleepingSession.getStartSleeping().isBefore(sleepingSession.getEndSleeping()))
                .collect(Collectors.toList());
    }
}
