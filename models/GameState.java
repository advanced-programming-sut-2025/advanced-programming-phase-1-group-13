package models;

import models.enums.environment.Season;
import models.enums.environment.Time;
import models.enums.environment.Weather;

import java.util.HashMap;

public class GameState {
    private int cropGrowthRate;
    private boolean automaticIrrigation;
    private int energyUsageRate;
    private boolean possibilityOfThor;
    private Time time;
    private Weather currentWeather;

    public void modifyState(String key, int value) {
        // TODO
        if (this.currentWeather == Weather.STORM) {
            this.possibilityOfThor = true;
        } else {
            this.possibilityOfThor = false;
        }
    }

    public int getCropGrowthRate() {
        return cropGrowthRate;
    }

    public boolean isAutomaticIrrigation() {
        return automaticIrrigation;
    }

    public int getEnergyUsageRate() {
        return energyUsageRate;
    }

    public boolean isPossibilityOfThor() {
        return possibilityOfThor;
    }

    public Time getTime() {
        return time;
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }
}
