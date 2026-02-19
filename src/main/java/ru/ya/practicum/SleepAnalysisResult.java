package ru.ya.practicum;

import java.util.Objects;

public class SleepAnalysisResult<T> {
    private final String description;
    private final T result;

    public SleepAnalysisResult(T result, String description) {
        this.description = description;
        this.result = result;
    }

    public String getDescription() {
        return description;
    }

    public T getResult() {
        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SleepAnalysisResult<?> that = (SleepAnalysisResult<?>) o;
        return Objects.equals(description, that.description) &&
                Objects.equals(result, that.result);
    }

    @Override
    public int hashCode() {
        return Objects.hash(description, result);
    }
}
