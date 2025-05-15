package models.enums.environment;

public enum Weekday {
    MERCDAY("Mercday"),
    VENDAY("Venday"),
    EARTHDAY("Earthday"),
    MARSADY("Marsady"),
    JUPYDAY("Jupyday"),
    URANDAY("Uranday"),
    NEPDAY("Nepday"),;

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