package models;

import models.farming.Crop;
import models.farming.Harvestable;
import models.farming.Tree;

import java.util.ArrayList;

public class Farm {
    private ArrayList<Tile> mapTiles;
    private Cabin cabin;
    private Greenhouse greenhouse;
    private Quarry quarry;
    private ArrayList<Lake> lakes;
    private int cropCount;
    private ArrayList<Crop> plantedCrops;
    private ArrayList<Tree> trees;


    public void placeScarecrow(Position position) {

    }

    public void plant(models.PlantSource seed, Position position) {

    }

    public void showPlant(Position position) {

    }

    public void fertilize(Fertilizer fertilizer, Position position) {

    }

    public void harvest(Harvestable harvestable) {

    }

    public ArrayList<Tile> getMapTiles() {
        return this.mapTiles;
    }

    public Cabin getCabin() {
        return this.cabin;
    }

    public Greenhouse getGreenhouse() {
        return this.greenhouse;
    }

    public Quarry getQuarry() {
        return this.quarry;
    }

    public ArrayList<Lake> getLakes() {
        return this.lakes;
    }

    public ArrayList<Crop> getPlantedCrops() {
        return this.plantedCrops;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public int getCropCount() {
        return cropCount;
    }
}