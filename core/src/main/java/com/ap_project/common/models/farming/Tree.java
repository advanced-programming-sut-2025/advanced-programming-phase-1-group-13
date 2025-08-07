package com.ap_project.common.models.farming;

import com.ap_project.common.models.Item;
import com.ap_project.common.models.Position;
import com.ap_project.common.models.Tile;
import com.ap_project.common.models.enums.environment.Season;
import com.ap_project.common.models.enums.types.FruitType;
import com.ap_project.common.models.enums.types.TileType;
import com.ap_project.common.models.enums.types.TreeType;

import java.util.ArrayList;

public class Tree extends Item implements Harvestable {
    private final TreeType type;
    private PlantSource source;
    private int numOfStages;
    private final int totalHarvestTime;
    private int baseSellPrice;
    private boolean isEdible;
    private Integer energy;
    private ArrayList<Season> seasons;
    private Position position;
    private final FruitType fruitType;
    private final int fruitHarvestCycle;
    private boolean isBurnt;
    private int stage;
    private int dayInStage;
    private Integer daySinceLastHarvest;
    private boolean hasBeenWateredToday;
    private boolean hasBeenFertilizedToday;

    public Tree(TreeType treeType) {
        this.type = treeType;
        this.totalHarvestTime = treeType.getTotalHarvestTime();
        this.name = treeType.name();
        this.fruitType = type.getFruit();
        this.fruitHarvestCycle = type.getFruitHarvestCycle();
        this.hasBeenWateredToday = false;
        this.isBurnt = false;
    }

    public Tree(TreeType type, Tile tile) {
        if (tile == null) {
            throw new IllegalArgumentException("No tile found at this position");
        }
        tile.setType(TileType.TREE);
        this.position = tile.getPosition();

        this.type = type;
        this.name = type.getName();
        this.numOfStages = 5;
        this.totalHarvestTime = type.getTotalHarvestTime();
        this.baseSellPrice = type.getFruitBaseSellPrice();
        this.isEdible = type.isFruitEdible();
        this.energy = type.getFruitEnergy();
        this.seasons = type.getSeasons();
        this.fruitType = type.getFruit();
        this.fruitHarvestCycle = type.getFruitHarvestCycle();

        this.isBurnt = false;
        this.dayInStage = 0;
        this.stage = 1;

        this.hasBeenWateredToday = false;
    }

    public Tree(TreeType type, Tile tile, int stage) {
        if (tile == null) {
            throw new IllegalArgumentException("No tile found at this position");
        }
        tile.setType(TileType.TREE);
        this.position = tile.getPosition();

        this.type = type;
        this.name = type.getName();
        this.numOfStages = 4;
        this.totalHarvestTime = type.getTotalHarvestTime();
        this.baseSellPrice = type.getFruitBaseSellPrice();
        this.isEdible = type.isFruitEdible();
        this.energy = type.getFruitEnergy();
        this.seasons = type.getSeasons();
        this.fruitType = type.getFruit();
        this.fruitHarvestCycle = type.getFruitHarvestCycle();

        this.isBurnt = false;
        this.dayInStage = 0;
        this.stage = stage;
        if (stage == 5) {
            this.daySinceLastHarvest = 1;
        }

        this.hasBeenWateredToday = false;
    }

    public void showInfo() {

    }

    public TreeType getType() {
        return type;
    }

    public PlantSource getSource() {
        return source;
    }

    public int getNumOfStages() {
        return numOfStages;
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

    public Position getPosition() {
        return position;
    }

    public FruitType getFruitType() {
        return fruitType;
    }

    public boolean isBurnt() {
        return isBurnt;
    }

    public void setBurnt(boolean burnt) {
        isBurnt = burnt;
    }

    public int getStage() {
        return stage;
    }

    public void incrementStage() {
        this.stage++;
        if (this.stage > this.numOfStages) {
            this.stage = this.numOfStages;
        }
    }

    public int getDayInStage() {
        return dayInStage;
    }

    public void incrementDayInStage() {
        this.dayInStage++;
        if (dayInStage > 7) {
            dayInStage = 0;
            stage++;
        }

        if (stage == 5) daySinceLastHarvest = fruitHarvestCycle;
    }

    public Integer getDaySinceLastHarvest() {
        return this.daySinceLastHarvest;
    }

    public void setDaySinceLastHarvest(Integer daySinceLastHarvest) {
        this.daySinceLastHarvest = daySinceLastHarvest;
    }

    public void incrementDaySinceLastHarvest() {
        if (daySinceLastHarvest != null) {
            this.daySinceLastHarvest++;
        }
    }

    public boolean hasBeenWateredToday() {
        return this.hasBeenWateredToday;
    }

    public void setHasBeenWateredToday(boolean hasBeenWateredToday) {
        this.hasBeenWateredToday = hasBeenWateredToday;
    }

    public boolean hasBeenFertilizedToday() {
        return hasBeenFertilizedToday;
    }

    public void setHasBeenFertilizedToday(boolean hasBeenFertilizedToday) {
        this.hasBeenFertilizedToday = hasBeenFertilizedToday;
    }

    public boolean hasFruits() {
        return (stage == 5) && (daySinceLastHarvest >= fruitHarvestCycle);
    }

    @Override
    public String getName() {
        return this.type.getName();
    }
}
