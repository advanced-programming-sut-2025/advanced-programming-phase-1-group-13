package com.ap_project.models;

import com.ap_project.models.enums.types.TileType;
import com.ap_project.models.inventory.Refrigerator;

import java.util.ArrayList;

public class Cabin {
    private ArrayList<Tile> tiles;
    private Refrigerator refrigerator;
    private Position position;

    public Cabin() {
        this.tiles = new ArrayList<>();
        this.refrigerator = new Refrigerator();
        this.position = new Position();
    }

    public Refrigerator getRefrigerator() {
        return this.refrigerator;
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public Position getPosition() {
        return position;
    }

    public void setTiles(ArrayList<Position> positions) {
        ArrayList<Tile> tiles = new ArrayList<>();
        for (Position position : positions) {
            tiles.add(new Tile(position, TileType.CABIN));
        }
        this.tiles = tiles;
        this.position = positions.get(0);
    }
}
