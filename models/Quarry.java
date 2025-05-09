package models;

import java.util.ArrayList;

public class Quarry {
    private ArrayList<Tile> tiles;
    private ArrayList<Mineral> minerals;

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public ArrayList<Mineral> getMinerals() {
        return minerals;
    }

    public void setMinerals(ArrayList<Mineral> minerals) {
        this.minerals = minerals;
    }
}