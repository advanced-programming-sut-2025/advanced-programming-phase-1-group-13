package com.project.models.enums.environment;

import java.util.List;
import java.util.Random;

public enum Season {
    SPRING("Spring", List.of(Weather.SUNNY, Weather.RAINY, Weather.STORM)),
    SUMMER("Summer", List.of(Weather.SUNNY, Weather.RAINY, Weather.STORM)),
    FALL("Fall", List.of(Weather.SUNNY, Weather.RAINY, Weather.STORM)),
    WINTER("Winter", List.of(Weather.SUNNY, Weather.SNOW));


    private final String name;
    private final List<Weather> availableWeathers;

    Season(String name, List<Weather> availableWeathers) {
        this.name = name;
        this.availableWeathers = availableWeathers;
    }

    public String getName() {
        return name;
    }

    public List<Weather> getAvailableWeathers() {
        return availableWeathers;
    }

    public Season next() {
        int nextOrdinal = (this.ordinal() + 1) % values().length;
        return values()[nextOrdinal];
    }

    public Weather getRandomWeather() {
        return availableWeathers.get(new Random().nextInt(availableWeathers.size()));
    }
}
