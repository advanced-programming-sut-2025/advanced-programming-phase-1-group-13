package models;

import models.inventory.Refrigerator;

import java.util.ArrayList;

public class Cabin {
    private ArrayList<Position> tiles;
    private Refrigerator refrigerator;

    public Refrigerator getRefrigerator() {
        return this.refrigerator;
    }

    public ArrayList<Position> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Position> tiles) {
        this.tiles = tiles;
    }
}