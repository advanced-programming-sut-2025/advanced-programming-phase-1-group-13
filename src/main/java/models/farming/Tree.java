package models.farming;

import models.Item;
import models.Position;
import models.enums.environment.Season;
import models.enums.types.FruitType;
import models.enums.types.SeedType;

import java.util.ArrayList;

public class Tree extends Item implements Harvestable {
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
    private FruitType fruit;
    private int fruitHarvestCycle;
    private boolean isBurnt;
    private SeedType seedType;


    public Tree(Position pos) {
        super();
    }

    public void showInfo() {

    }

    public PlantSource getSource() {
        return source;
    }

    public int getNumOfStages() {
        return numOfStages;
    }

    public ArrayList<Integer> getStages() {
        return stages;
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

    public FruitType getFruit() {
        return fruit;
    }

    public int getFruitHarvestCycle() {
        return fruitHarvestCycle;
    }

    public boolean isBurnt() {
        return isBurnt;
    }

    public SeedType getSeed() {
        return seedType;
    }

    public Position getPosition() {
        return position;
    }
}