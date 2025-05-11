package models;

import java.util.ArrayList;

public class Greenhouse {
    private ArrayList<Tile> tiles;

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public static boolean canBuildGreenhouse() {
        // TODO: check if we have the required material
        // + is only ONE greenhouse allowed?
        return false;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }
}