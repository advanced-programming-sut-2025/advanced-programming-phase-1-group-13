package models.farming;

import models.Item;
import models.Position;
import models.enums.environment.Season;
import models.enums.types.FruitType;
import models.enums.types.Seed;

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
    private Seed seed;


    public Tree(Position pos) {
        super();
    }

    public void showInfo() {

    }

    public Position getPosition() {
        return position;
    }
}