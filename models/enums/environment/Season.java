package models.enums.environment;

public enum Season {
    SPRING,
    SUMMER,
    FALL,
    WINTER;

    public Season next() {
        int nextOrdinal = (this.ordinal() + 1) % values().length;
        return values()[nextOrdinal];
    }
}