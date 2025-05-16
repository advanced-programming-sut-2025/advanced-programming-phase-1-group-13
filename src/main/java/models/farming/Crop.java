package models.farming;

import models.Item;
import models.enums.environment.Season;
import models.enums.types.CropType;
import models.enums.types.SeedType;

import java.util.ArrayList;

public class Crop extends Item implements Harvestable {
    private final CropType type;
    private final SeedType source;
    private final int numOfStages;
    private final ArrayList<Integer> stagesTimes;
    private final int totalHarvestTime;
    private final int baseSellPrice;
    private final boolean isEdible;
    private final int energy;
    private final ArrayList<Season> seasons;
    private final boolean canBecomeGiant;
    private boolean isGiant;
    private final boolean oneTime;
    private final int regrowthTime;
    private int dayInStage;
    private int stage;

    public Crop(CropType type) {

        this.type = type;
        this.source = type.getSource();
        this.numOfStages = type.getNumberOfStages();
        this.stagesTimes = type.getStages();
        this.totalHarvestTime = type.getTotalHarvestTime();
        this.baseSellPrice = type.getSellPrice();
        this.isEdible = type.isEdible();
        this.energy = type.getEnergy();
        this.seasons = type.getSeasons();
        this.canBecomeGiant = type.canBecomeGiant();
        this.oneTime = type.isOneTime();
        this.regrowthTime = type.getRegrowthTime();

        this.isGiant = false;
        this.dayInStage = 0;
        this.stage = 0;
    }

    public CropType getType() {
        return type;
    }

    public SeedType getSource() {
        return source;
    }

    public int getNumOfStages() {
        return numOfStages;
    }

    public ArrayList<Integer> getStagesTimes() {
        return stagesTimes;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public int getEnergy() {
        return energy;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    public boolean isGiant() {
        return isGiant;
    }

    public void setGiant(boolean giant) {
        isGiant = giant;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public int getRegrowthTime() {
        return regrowthTime;
    }

    public int getDayInStage() {
        return dayInStage;
    }

    public void setDayInStage(int dayInStage) {
        this.dayInStage = dayInStage;
    }

    public int getStage() {
        return stage;
    }

    public void setStage(int stage) {
        this.stage = stage;
    }

    public void showInfo() {

    }
}