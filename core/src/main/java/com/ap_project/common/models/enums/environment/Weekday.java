package com.ap_project.common.models.enums.environment;

public enum Weekday {
    SATURDAY("Saturday"),
    SUNDAY("Sunday"),
    MONDAY("Monday"),
    TUESDAY("Tuesday"),
    WEDNESDAY("Wednesday"),
    THURSDAY("Thursday"),
    FRIDAY("Friday"),;

    private final String name;

    Weekday(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Weekday next() {
        int nextOrdinal = (this.ordinal() + 1) % values().length;
        return values()[nextOrdinal];
    }
}
