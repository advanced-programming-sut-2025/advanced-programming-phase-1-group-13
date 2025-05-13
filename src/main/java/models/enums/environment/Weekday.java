package models.enums.environment;

public enum Weekday {
    MERCDAY,
    VENDAY,
    EARTHDAY,
    MARSADY,
    JUPYDAY,
    URANDAY,
    NEPDAY;

    public Weekday next() {
        int nextOrdinal = (this.ordinal() + 1) % values().length;
        return values()[nextOrdinal];
    }
}