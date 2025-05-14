package models.farming;

import models.Item;
import models.Position;
import models.enums.environment.Season;
import models.enums.types.CropType;

import java.util.ArrayList;

public class Crop extends Item implements Harvestable {
    private final CropType type;
    private PlantSource source;
    private int numOfStages;
    private ArrayList<Integer> stages;
    private int totalHarvestTime;
    private int baseSellPrice;
    private boolean isEdible;
    private int energy;
    private ArrayList<Season> seasons;
    private boolean canBecomeGiant;
    private boolean isGiant;
    private Position position;
    private boolean oneTime;
    int regrowthTime;

    public Crop(CropType type) {
        this.type = type;
    }

    public CropType getType() {
        return type;
    }

    public PlantSource getSource() {
        return source;
    }

    public void setSource(PlantSource source) {
        this.source = source;
    }

    public int getNumOfStages() {
        return numOfStages;
    }

    public void setNumOfStages(int numOfStages) {
        this.numOfStages = numOfStages;
    }

    public ArrayList<Integer> getStages() {
        return stages;
    }

    public void setStages(ArrayList<Integer> stages) {
        this.stages = stages;
    }

    public int getTotalHarvestTime() {
        return totalHarvestTime;
    }

    public void setTotalHarvestTime(int totalHarvestTime) {
        this.totalHarvestTime = totalHarvestTime;
    }

    public int getBaseSellPrice() {
        return baseSellPrice;
    }

    public void setBaseSellPrice(int baseSellPrice) {
        this.baseSellPrice = baseSellPrice;
    }

    public boolean isEdible() {
        return isEdible;
    }

    public void setEdible(boolean edible) {
        isEdible = edible;
    }

    public int getEnergy() {
        return energy;
    }

    public void setEnergy(int energy) {
        this.energy = energy;
    }

    public ArrayList<Season> getSeasons() {
        return seasons;
    }

    public void setSeasons(ArrayList<Season> seasons) {
        this.seasons = seasons;
    }

    public boolean isCanBecomeGiant() {
        return canBecomeGiant;
    }

    public void setCanBecomeGiant(boolean canBecomeGiant) {
        this.canBecomeGiant = canBecomeGiant;
    }

    public boolean isGiant() {
        return isGiant;
    }

    public void setGiant(boolean giant) {
        isGiant = giant;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public boolean isOneTime() {
        return oneTime;
    }

    public void setOneTime(boolean oneTime) {
        this.oneTime = oneTime;
    }

    public int getRegrowthTime() {
        return regrowthTime;
    }

    public void setRegrowthTime(int regrowthTime) {
        this.regrowthTime = regrowthTime;
    }

    public void showInfo() {

    }
}