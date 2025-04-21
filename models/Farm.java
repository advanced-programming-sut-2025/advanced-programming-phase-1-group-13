package models;

import models.enums.types.FertilizerType;
import models.farming.Crop;
import models.farming.Harvestable;
import models.farming.PlantSource;
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
    private int mapNumberToFollow;
    private ArrayList<FarmBuilding> farmBuildings;

    public Farm(int mapNumberToFollow) {
        this.mapNumberToFollow = mapNumberToFollow;
        this.cropCount = 0;
        this.plantedCrops = new ArrayList<>();
        this.trees = new ArrayList<>();

        if (mapNumberToFollow == 1) {
            // TODO
            this.cabin = new Cabin(); // with map 1 properties
            this.lakes = new ArrayList<>(); // with map1 properties
            this.quarry = new Quarry(); // with map1 properties
            this.mapTiles = new ArrayList<>(); // with map1 properties
        } else if (mapNumberToFollow == 2) {
            // TODO
            this.cabin = new Cabin(); // with map 2 properties
            this.lakes = new ArrayList<>(); // with map2 properties
            this.quarry = new Quarry(); // with map2 properties
            this.mapTiles = new ArrayList<>(); // with map2 properties
        }
    }

    public void placeScarecrow(Position position) {

    }

    public void plant(PlantSource seed, Position position) {
        // TODO

    }

    public void showPlant(Position position) {
        // TODO

    }

    public void fertilize(FertilizerType fertilizer, Position position) {
        // TODO
    }

    public void harvest(Harvestable harvestable) {
        // TODO
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

    public ArrayList<FarmBuilding> getFarmBuildings() {
        return farmBuildings;
    }

    public Tile getTileByPosition(Position position) {
        for (Tile tile : mapTiles) {
            if (tile.getPosition().equals(position)) {
                return tile;
            }
        }
        return null;
    }
}