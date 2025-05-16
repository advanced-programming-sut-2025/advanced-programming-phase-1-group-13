package models;

import models.enums.types.TileType;

import java.util.ArrayList;

public class Greenhouse {
    private ArrayList<Tile> tiles;
    private boolean canEnter;

    public Greenhouse(ArrayList<Tile> tiles, boolean canEnter) {
        this.tiles = tiles;
        this.canEnter = canEnter;
    }

    public Greenhouse generateGreenhouse(int x, int y, int width, int height) {
        Greenhouse greenhouse = new Greenhouse();
        ArrayList<Tile> tiles = new ArrayList<>();
        setCanEnter(true);

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                Tile tile = new Tile();
                tile.setPosition(new Position(i, j));
                tile.setType(TileType.GREENHOUSE);
                tiles.add(tile);
            }
        }

        greenhouse.setTiles(tiles);
        return greenhouse;
    }


    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public static boolean canBuildGreenhouse() {
        int requiredWood = 500;
        int requiredStone = 1000;

        boolean hasEnoughResources = App.getLoggedIn().getWoodCount() >= requiredWood && App.getLoggedIn().getStoneCount() >= requiredStone;
        boolean greenhouseExists = App.getCurrentGame().getGameMap().getGreenhouse() != null;

        return hasEnoughResources && !greenhouseExists;
    }

    public void setCanEnter(boolean canEnter) {
        this.canEnter = canEnter;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }
}