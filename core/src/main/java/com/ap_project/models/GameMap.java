package com.ap_project.models;

import com.ap_project.models.enums.types.ForagingCropType;
import com.ap_project.models.enums.types.TreeType;
import com.ap_project.models.farming.ForagingCrop;
import com.ap_project.models.farming.Tree;
import com.ap_project.models.enums.types.TileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public final class GameMap {
    private static final int MAP_SIZE = 100;
    private static final int CABIN_SIZE = 10;
    private final ArrayList<Farm> farms;
    private final int mapNumber;
    private Lake lake;
    private Greenhouse greenhouse;
    private Cabin cabin;
    private Quarry quarry;
    private final ArrayList<Tree> trees;
    private final ArrayList<Mineral> stones;
    private final ArrayList<Tile> woodLogs;
    private final ArrayList<ForagingCrop> foragings;
    private transient Random random = new Random();
    private static ArrayList<Tile> allTiles;

    public GameMap(int mapNumber) {
        this.mapNumber = mapNumber;
        this.random = new Random();
        this.farms = new ArrayList<>();
        this.trees = new ArrayList<>();
        this.stones = new ArrayList<>();
        this.foragings = new ArrayList<>();
        this.woodLogs = new ArrayList<>();

        generateBaseMapTiles();
        generateFixedElements();
        consolidateAllTiles();
        generateRandomElements();
    }

    private void generateBaseMapTiles() {
        allTiles = new ArrayList<>(MAP_SIZE * MAP_SIZE);

        for (int x = 0; x < MAP_SIZE; x++) {
            for (int y = 0; y < MAP_SIZE; y++) {
                Position pos = new Position(x, y);
                Tile tile = new Tile(pos, TileType.NOT_PLOWED_SOIL);
                tile.setPosition(pos);
                tile.setType(TileType.NOT_PLOWED_SOIL);
                allTiles.add(tile);

                Tile verifyTile = getTileByPosition(pos);
                if (verifyTile == null || !verifyTile.getPosition().equals(pos)) {
                    throw new IllegalStateException("Failed to properly create tile at " + pos);
                }
            }
        }
    }

    public static int getMAP_SIZE() {
        return MAP_SIZE;
    }

    public int getCABIN_SIZE() {
        return CABIN_SIZE;
    }

    private void generateFixedElements() {
        if (mapNumber == 1) {
            this.lake = generateLake(10, 15, 5, 8);
            this.cabin = generateCabin(3, 3, 4, 4);
            this.greenhouse = generateGreenhouse();
            this.quarry = generateQuarry(20, 50, 10, 10);
        } else if (mapNumber == 2) {
            this.lake = generateLake(50, 10, 8, 5);
            this.cabin = generateCabin(4, 4, 4, 4);
            this.greenhouse = generateGreenhouse();
            this.quarry = generateQuarry(40, 30, 15, 10);
        }
    }

    public ArrayList<Tile> getWoodLogs() {
        return woodLogs;
    }

    private void generateRandomElements() {
        ArrayList<Position> availablePositions = new ArrayList<>();
        for (Tile tile : allTiles) {
            if (tile.getType() == TileType.NOT_PLOWED_SOIL && !isPositionOccupied(tile.getPosition())) {
                availablePositions.add(tile.getPosition());
            }
        }

        Collections.shuffle(availablePositions, random);

        int treeCount = Math.min(120 + random.nextInt(11), availablePositions.size());
        for (int i = 0; i < treeCount; i++) {
            Position pos = availablePositions.get(i);
            Tile tile = getTileByPosition(pos);
            Tree tree = new Tree(TreeType.getRandomTreeType(), tile);
            trees.add(tree);
            tile.setType(TileType.TREE);
            tile.pLaceItemOnTile(tree);
        }

        int stoneCount = Math.min(50 + random.nextInt(11), availablePositions.size() - treeCount);
        for (int i = 0; i < stoneCount; i++) {
            Position pos = availablePositions.get(treeCount + i);
            Mineral stone = new Mineral(pos);
            stones.add(stone);
            Tile tile = getTileByPosition(pos);
            tile.pLaceItemOnTile(stone);
            tile.setType(TileType.STONE);
        }

        int foragingCount = Math.min(30 + random.nextInt(6),
                availablePositions.size() - treeCount - stoneCount);
        for (int i = 0; i < foragingCount; i++) {
            Position pos = availablePositions.get(treeCount + stoneCount + i);
            foragings.add(new ForagingCrop(ForagingCropType.getRandomForagingCropType(), pos));
            getTileByPosition(pos).setType(TileType.GRASS);
        }

        int woodCount = Math.min(50 + random.nextInt(11),
                availablePositions.size() - treeCount - stoneCount - foragingCount);
        for (int i = 0; i < woodCount; i++) {
            Position pos = availablePositions.get(treeCount + stoneCount + foragingCount + i);
            Tile woodLog = getTileByPosition(pos);
            woodLog.setType(TileType.WOOD_LOG);
            woodLogs.add(woodLog);
        }
    }

    private Position getRandomPosition() {
        while (true) {
            int x = random.nextInt(MAP_SIZE);
            int y = random.nextInt(MAP_SIZE);
            Position pos = new Position(x, y);

            if (!isPositionOccupied(pos)) {
                return pos;
            }
        }
    }

    private boolean isPositionOccupied(Position pos) {
        // First check if tile exists and is basic soil
        Tile tile = getTileByPosition(pos);
        if (tile == null || tile.getType() != TileType.NOT_PLOWED_SOIL) {
            return true;
        }

        // Check fixed elements
        if (lake != null && lake.getTiles().contains(pos)) return true;
        if (quarry != null && quarry.getTiles().stream()
                .anyMatch(t -> t.getPosition().equals(pos))) return true;
        if (greenhouse != null && greenhouse.getTiles().stream()
                .anyMatch(t -> t.getPosition().equals(pos))) return true;
        if (cabin != null && cabin.getTiles().contains(pos)) return true;

        // Check dynamic elements
        for (Tree tree : trees) {
            if (tree.getPosition().equals(pos)) return true;
        }
        for (Mineral stone : stones) {
            if (stone.getPosition().equals(pos)) return true;
        }
        for (ForagingCrop crop : foragings) {
            if (crop.getPosition().equals(pos)) return true;
        }
        for (Tile log : woodLogs) {
            if (log.getPosition().equals(pos)) return true;
        }

        return false;
    }

    private Lake generateLake(int x, int y, int width, int height) {
        Lake lake = new Lake();
        ArrayList<Position> positions = generateTilePositions(x, y, width, height);
        lake.setTiles(positions);

        for (Position pos : positions) {
            Tile tile = getTileByPosition(pos);
            if (tile == null) {
                tile = new Tile(pos, TileType.WATER);
                tile.setPosition(pos);
                tile.setType(TileType.WATER);
                allTiles.add(tile);
            } else {
                tile.setType(TileType.WATER);
            }
        }

        return lake;
    }

    private Cabin generateCabin(int x, int y, int width, int height) {
        Cabin cabin = new Cabin();
        ArrayList<Position> positions = generateTilePositions(x, y, width, height);
        cabin.setTiles(positions);

        for (Position pos : positions) {
            Tile tile = getTileByPosition(pos);
            if (tile == null) {
                tile = new Tile(pos, TileType.CABIN);
                tile.setPosition(pos);
                tile.setType(TileType.CABIN);
                allTiles.add(tile);
            } else {
                tile.setType(TileType.CABIN);
            }
        }
        return cabin;
    }

    private ArrayList<Position> generateTilePositions(int x, int y, int width, int height) {
        ArrayList<Position> tiles = new ArrayList<>();

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                tiles.add(new Position(i, j));
            }
        }

        return tiles;
    }

    public Greenhouse generateGreenhouse() {
        return new Greenhouse();
    }

    public void activateGreenhouse() {
        this.greenhouse = generateGreenhouse();
        this.greenhouse.setCanEnter(true);
    }

    private Quarry generateQuarry(int x, int y, int width, int height) {
        Quarry quarry = new Quarry();
        ArrayList<Tile> tiles = new ArrayList<>();
        ArrayList<Mineral> minerals = new ArrayList<>();

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                Tile tile = new Tile(new Position(i, j), TileType.QUARRY_GROUND);
                tile.setPosition(new Position(i, j));
                tile.setType(TileType.QUARRY_GROUND);
                tiles.add(tile);
            }
        }

        int mineralCount = 3 + random.nextInt(6);
        for (int i = 0; i < mineralCount; i++) {
            int mineralX = x + random.nextInt(width);
            int mineralY = y + random.nextInt(height);
            minerals.add(new Mineral(new Position(mineralX, mineralY)));
        }

        quarry.setTiles(tiles);
        quarry.setMinerals(minerals);
        return quarry;
    }

    public ArrayList<Farm> getFarms() {
        return farms;
    }

    public int getMapNumber() {
        return mapNumber;
    }

    public Lake getLake() {
        return lake;
    }

    public Greenhouse getGreenhouse() {
        return this.greenhouse;
    }

    public Cabin getCabin() {
        return cabin;
    }

    public Quarry getQuarry() {
        return quarry;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public ArrayList<Mineral> getStones() {
        return stones;
    }

    public ArrayList<ForagingCrop> getForagings() {
        return foragings;
    }

    public Random getRandom() {
        return random;
    }

    public ArrayList<Tile> getAllTiles() {
        return allTiles;
    }

    public void consolidateAllTiles() {
        if (greenhouse != null) {
            for (Tile tile : greenhouse.getTiles()) {
                Tile baseTile = getTileByPosition(tile.getPosition());
                if (baseTile != null) baseTile.setType(tile.getType());
                else allTiles.add(tile);
            }
        }

        if (quarry != null) {
            for (Tile tile : quarry.getTiles()) {
                Tile baseTile = getTileByPosition(tile.getPosition());
                if (baseTile != null) baseTile.setType(tile.getType());
                else allTiles.add(tile);
            }
        }

        if (lake != null) {
            for (Position pos : lake.getTiles()) {
                Tile tile = getTileByPosition(pos);
                if (tile != null) tile.setType(TileType.WATER);
            }
        }

        for (Farm farm : farms) {
            for (Tile tile : farm.getFarmTiles()) {
                Tile baseTile = getTileByPosition(tile.getPosition());
                if (baseTile != null) baseTile.setType(tile.getType());
                else allTiles.add(tile);
            }
        }
    }


    public Tile getTileByPosition(Position position) {
        for (Tile tile : this.getAllTiles()) {
            if (tile.getPosition().getX() == position.getX() &&
                    tile.getPosition().getY() == position.getY()) {
                return tile;
            }
        }
        return null;
    }

//    public Item getItemByPosition(Position position) {
//        Tile tile = this.getTileByPosition(position);
//        if (tile == null) return null;
//        return tile.getItemPlacedOnTile();
//    }
}
