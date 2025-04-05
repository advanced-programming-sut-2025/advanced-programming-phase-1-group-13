package models;

import models.farming.Crop;
import models.farming.Harvestable;
import models.farming.Tree;

import java.util.ArrayList;

public class Farm {
    private Cabin cabin;
    private Greenhouse greenhouse;
    private Quarry quarry;
    private ArrayList<Lake> lakes;
    private int cropCount;
    private ArrayList<Crop> plantedCrops;
    private ArrayList<Tree> trees;

    public void placeScarecrow(Position position){

    }

    public void plant(models.PlantSource seed, Position position){

    }

    public void showPlant(Position position){

    }

    public void fertilize(Fertilizer fertilizer , Position position){

    }

    public void harvest(Harvestable harvestable){

    }

}