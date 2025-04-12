package models.farming;

import models.Position;
import models.enums.environment.Season;

import java.util.ArrayList;

public abstract class Harvestable {
    private String name;
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

    public void showInfo() {
        // TODO
    }
}
