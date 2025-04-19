package models.farming;

import models.Item;
import models.Position;
import models.enums.environment.Season;
import models.enums.types.CropType;

import java.util.ArrayList;

public class Crop extends Item implements Harvestable {
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
    private CropType type;
    private boolean oneTime;
    int regrowthTime;

    public Crop(CropType type) {
        this.type = type;
    }

    public void showInfo() {

    }
}
