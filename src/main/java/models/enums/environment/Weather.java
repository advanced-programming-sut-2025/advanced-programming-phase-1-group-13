package models.enums.environment;

public enum Weather {
    SUNNY("Sunny"),
    RAINY("Rainy"),
    STORM("Stormy"),
    SNOW("Snowy"),;

    private final String name;

    Weather(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
