package com.project.models;

import com.google.gson.GsonBuilder;
import com.project.models.enums.environment.Time;
import com.project.models.enums.environment.Weather;
import com.project.models.enums.types.TileType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class GameState {
    private int cropGrowthRate;
    private boolean automaticIrrigation;
    private int energyUsageRate;
    private boolean possibilityOfThor;
    private Time time;
    private Weather currentWeather;

    public GameState() {
        this.cropGrowthRate = 0; // TODO
        this.automaticIrrigation = false; // TODO
        this.energyUsageRate = 0; // TODO
        this.possibilityOfThor = false; // TODO
        this.time = new Time();
        this.currentWeather = Weather.getRandomWeather();
    }

    public void setCropGrowthRate(int cropGrowthRate) {
        this.cropGrowthRate = cropGrowthRate;
    }

    public void setAutomaticIrrigation(boolean automaticIrrigation) {
        this.automaticIrrigation = automaticIrrigation;
    }

    public void setPossibilityOfThor(boolean possibilityOfThor) {
        this.possibilityOfThor = possibilityOfThor;
    }

    public void setTime(Time time) {
        this.time = time;
        //saveGameState();
    }

    public void setCurrentWeather(Weather currentWeather) {
        this.currentWeather = currentWeather;
        //saveGameState();
    }

    private void saveGameState() {
        try (FileWriter writer = new FileWriter("gameState.json")) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

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

    public boolean getPossibilityOfThor() {
        return possibilityOfThor;
    }

    public Time getTime() {
        return time;
    }

    public Weather getCurrentWeather() {
        return currentWeather;
    }

    public void triggerLightningStrike() {
        if (this.currentWeather == Weather.STORM) {
            this.possibilityOfThor = true;
            for (Farm farm : App.getCurrentGame().getGameMap().getFarms()) {
                strikeRandomTiles(farm);
            }
        } else {
            this.possibilityOfThor = false;
        }
    }

    private void strikeRandomTiles(Farm farm) {
        Random random = new Random();
        int strikeCount = 3;

        for (int i = 0; i < strikeCount; i++) {
            Position strikePosition = farm.getRandomTilePosition();
            Tile tile = App.getCurrentGame().getGameMap().getTileByPosition(strikePosition);
            if (tile != null) {
                tile.setType(TileType.STONE);
            }
        }
    }
}
