package models;

import models.enums.types.TileType;

import java.util.ArrayList;

public class Greenhouse {
    private ArrayList<Tile> tiles;
    private boolean canEnter;

    public Greenhouse() {
        this.tiles = new ArrayList<>();
        this.canEnter = false;
        generateFixedGreenhouseTiles();
    }

    private void generateFixedGreenhouseTiles() {
        int startX = 20;
        int startY = 20;
        int width = 5;
        int height = 5;

        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + height; j++) {
                Tile tile = new Tile();
                tile.setPosition(new Position(i, j));
                tile.setType(TileType.GREENHOUSE);
                tiles.add(tile);
            }
        }
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public boolean isCanEnter() {
        return canEnter;
    }

    public void setCanEnter(boolean canEnter) {
        this.canEnter = canEnter;
    }

    public static boolean canBuildGreenhouse() {
        int requiredWood = 500;
        int requiredStone = 1000;

        boolean hasEnoughResources = App.getLoggedIn().getWoodCount() >= requiredWood &&
                App.getLoggedIn().getStoneCount() >= requiredStone;

        boolean greenhouseExists = App.getCurrentGame().getGameMap().getGreenhouse() != null;

        return hasEnoughResources && !greenhouseExists;
    }
}
