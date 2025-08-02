package com.ap_project.models;

import java.util.ArrayList;

public class Lake {
    private ArrayList<Position> tiles;
    private Position position;

    public ArrayList<Position> getTiles() {
        return tiles;
    }

    public Position getPosition() {
        return position;
    }

    public void setTiles(ArrayList<Position> tiles) {
        this.tiles = tiles;
        this.position = tiles.get(0);
    }
}
