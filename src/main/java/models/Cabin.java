package models;

import models.enums.types.TileType;
import models.inventory.Refrigerator;

import java.util.ArrayList;

public class Cabin {
    private ArrayList<Tile> tiles;
    private Refrigerator refrigerator;

    public Cabin() {
        this.tiles = new ArrayList<>();
        this.refrigerator = new Refrigerator();
    }

    public Refrigerator getRefrigerator() {
        return this.refrigerator;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Position> positions) {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (Position position : positions) {
            tiles.add(new Tile(position, TileType.CABIN));
        }
        this.tiles = tiles;
    }
}