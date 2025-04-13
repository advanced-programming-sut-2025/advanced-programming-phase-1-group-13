package models;

import java.util.ArrayList;

public class GameMap {
    private ArrayList<Farm> farms;
    private ArrayList<Shop> shops;
    private int mapNumber;

    // TODO
    public GameMap(int mapNumber) {
        this.farms = new ArrayList<>();
        this.shops = new ArrayList<>();
    }
}
