package ru.ya.practicum;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class SleepLogReader {
    public static List<String> readSleepLog(String fileName) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(Path.of(fileName), StandardCharsets.UTF_8)) {
            return br.lines()
                    .filter(l -> !l.isBlank())
                    .collect(Collectors.toList());
        }
    }
}
