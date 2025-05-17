package models.farming;

import controllers.GameController;
import models.App;
import models.Item;
import models.Position;
import models.Tile;
import models.enums.environment.Season;
import models.enums.types.FruitType;
import models.enums.types.SeedType;
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
    private FruitType fruit;
    private int fruitHarvestCycle;
    private boolean isBurnt;
    private int stage;
    private int dayInStage;


    public Tree(TreeType type, Position position, Tile tile) {
        if (tile == null) {
            throw new IllegalArgumentException("No tile found at position: " + position);
        }
        tile.setType(TileType.TREE);
        this.position = position;

        this.type = type;
        this.numOfStages = 4;
        this.totalHarvestTime = type.getTotalHarvestTime();
        this.baseSellPrice = type.getFruitBaseSellPrice();
        this.isEdible = type.isFruitEdible();
        this.energy = type.getFruitEnergy();
        this.seasons = type.getSeasons();
        this.fruit = type.getFruit();
        this.fruitHarvestCycle = type.getFruitHarvestCycle();

        this.isBurnt = false;
        this.dayInStage = 0;
        this.stage = 0;
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

    public FruitType getFruit() {
        return fruit;
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

    public void setStage(int stage) {
        this.stage = stage;
    }

    public int getDayInStage() {
        return dayInStage;
    }

    public void setDayInStage(int dayInStage) {
        this.dayInStage = dayInStage;
    }
}