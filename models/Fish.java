package models;

import models.enums.environment.Season;
import models.enums.types.FishType;

public class Fish {
    FishType name;
    int basePrice;
    Season season;
    boolean isLegendary;

    public Fish(FishType name, int basePrice, Season season, boolean isLegendary) {
        this.name = name;
        this.basePrice = basePrice;
        this.season = season;
        this.isLegendary = isLegendary;
    }

    public FishType getName() {
        return name;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public Season getSeason() {
        return season;
    }

    public boolean isLegendary() {
        return isLegendary;
    }

    public void setName(FishType name) {
        this.name = name;
    }

    public void setBasePrice(int basePrice) {
        this.basePrice = basePrice;
    }

    public void setSeason(Season season) {
        this.season = season;
    }

    public void setLegendary(boolean legendary) {
        isLegendary = legendary;
    }
}
