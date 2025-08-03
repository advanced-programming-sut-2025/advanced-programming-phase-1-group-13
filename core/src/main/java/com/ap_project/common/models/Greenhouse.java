package com.ap_project.common.models;

import com.ap_project.common.models.enums.types.TileType;

import java.util.ArrayList;

public class Greenhouse {
    private ArrayList<Tile> tiles;
    private boolean canEnter;
    private Position position;

    public Greenhouse() {
        this.tiles = new ArrayList<>();
        this.canEnter = false;
        generateFixedGreenhouseTiles();
    }

    private void generateFixedGreenhouseTiles() {
        int startX = 20;
        int startY = 20;
        int width = 7;
        int height = 6;

        for (int i = startX; i < startX + width; i++) {
            for (int j = startY; j < startY + height; j++) {
                Tile tile = new Tile(new Position(i, j), TileType.GREENHOUSE);
                tile.setPosition(new Position(i, j));
                tile.setType(TileType.GREENHOUSE);
                tiles.add(tile);
            }
        }

        this.position = tiles.get(0).getPosition();
    }

    public ArrayList<Tile> getTiles() {
        return tiles;
    }

    public Position getPosition() {
        return position;
    }

    public void setTiles(ArrayList<Tile> tiles) {
        this.tiles = tiles;
    }

    public boolean canEnter() {
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

        boolean greenhouseExists = App.getCurrentGame().getPlayerByUsername(App.getLoggedIn().getUsername()).getFarm().getGreenhouse().canEnter;

        return hasEnoughResources && !greenhouseExists;
    }

    public static boolean isPositionInGreenhouse(Position pos) { // fixed greenhouse position in all gameMaps.
        if (pos.getX() >= 20 && pos.getY() >= 20 && pos.getX() < 25 && pos.getY() < 25) {
            return true;
        }
        return false;
    }
}
