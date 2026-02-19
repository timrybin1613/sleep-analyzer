package ru.ya.practicum;

public enum Chronotype {
    PIGEON("Голубь"),
    OWL("Сова"),
    LARK("Жаворонок");

    private final String title;

    Chronotype(String title) {
        this.title = title;
    }

    public String getTitle() {
        return title;
    }
}

