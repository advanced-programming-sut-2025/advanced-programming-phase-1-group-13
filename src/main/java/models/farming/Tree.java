package models.farming;

import models.Item;
import models.Position;
import models.Tile;
import models.enums.environment.Season;
import models.enums.types.FruitType;
import models.enums.types.TileType;
import models.enums.types.TreeType;

import java.util.ArrayList;

public class Tree extends Item implements Harvestable {
    private TreeType type;
    private PlantSource source;
    private int numOfStages;
    private int totalHarvestTime;
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
    private String name;

    public Tree(TreeType treeType) {
        this.type = treeType;
        this.name = treeType.name();
        this.fruitType = type.getFruit();
        this.fruitHarvestCycle = type.getFruitHarvestCycle();
        this.hasBeenWateredToday = false;
    }

    public Tree(TreeType type, Tile tile) {
        if (tile == null) {
            throw new IllegalArgumentException("No tile found at position: " + tile.getPosition());
        }
        tile.setType(TileType.TREE);
        this.position = tile.getPosition();

        this.type = type;
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
        this.stage = 0;

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

    public int getFruitHarvestCycle() {
        return fruitHarvestCycle;
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
    }

    public Integer getDaySinceLastHarvest() {
        return this.daySinceLastHarvest;
    }

    public void setDaySinceLastHarvest(Integer daySinceLastHarvest) {
        this.daySinceLastHarvest = daySinceLastHarvest;
    }

    public void incrementDaySinceLastHarvest() {
        this.daySinceLastHarvest++;
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

    @Override
    public String getName() {
        return this.name;
    }
}