package ru.ya.practicum;

import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SleepDataExtractor implements Function<List<String>, List<String[]>> {
    @Override
    public List<String[]> apply(List<String> lines) {
        return lines.stream()
                .map(line -> line.split(";"))
                .filter(p -> p.length == 3)
                .collect(Collectors.toList());
    }
}
