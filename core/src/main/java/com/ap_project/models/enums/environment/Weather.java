package com.ap_project.models.enums.environment;

import java.util.Random;

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

    public static Weather getRandomWeather() {
        return Weather.values()[(new Random()).nextInt(Weather.values().length)];
    }
}
