package models;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import models.enums.types.MineralType;
import models.farming.ForagingCrop;
import models.farming.Tree;
import models.enums.types.TileType;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

public final class GameMap {
    private static final int MAP_SIZE = 80;
    private static final int CABIN_SIZE = 10;
    private ArrayList<Farm> farms;
    private ArrayList<Shop> shops;
    private int mapNumber;
    private Lake lake;
    private Greenhouse greenhouse;
    private Cabin cabin;
    private Quarry quarry;
    private ArrayList<Tree> trees;
    private ArrayList<Mineral> stones;
    private ArrayList<Tile> woodLogs;
    private ArrayList<ForagingCrop> foragings;
    private transient Random random = new Random();
    private static ArrayList<Tile> allTiles;

    public GameMap(int mapNumber) {
        this.mapNumber = mapNumber;
        this.random = new Random();
        this.farms = new ArrayList<>();
        this.shops = new ArrayList<>();
        this.trees = new ArrayList<>();
        this.stones = new ArrayList<>();
        this.foragings = new ArrayList<>();

        generateFixedElements();
        generateRandomElements();
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
            this.cabin = generateCabin(70, 70);
            this.greenhouse = generateGreenhouse(60, 60, 3, 4);
            this.quarry = generateQuarry(20, 50, 10, 15);
        } else if (mapNumber == 2) {
            this.lake = generateLake(50, 10, 8, 5);
            this.cabin = generateCabin(10, 70);
            this.greenhouse = generateGreenhouse(70, 10, 4, 3);
            this.quarry = generateQuarry(40, 30, 15, 10);
        }
        saveGameMap();
    }

    private void saveGameMap() {
        try (FileWriter writer = new FileWriter("gameMap.json")) {
            writer.write(new GsonBuilder().setPrettyPrinting().create().toJson(this));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void generateRandomElements() {
        int treeCount = 40 + random.nextInt(11);
        for (int i = 0; i < treeCount; i++) {
            Position pos = getRandomPosition();
            trees.add(new Tree(pos));
        }

        int stoneCount = 10 + random.nextInt(11);
        for (int i = 0; i < stoneCount; i++) {
            Position pos = getRandomPosition();
            stones.add(new Mineral(pos));
        }

        // TODO: CHECK!!!
        int foragingCount = 3 + random.nextInt(6);
        for (int i = 0; i < foragingCount; i++) {
            Position pos = getRandomPosition();
            foragings.add(new ForagingCrop(pos));
        }

        int woodCount = 10 + random.nextInt(11);
        for (int i = 0; i < woodCount; i++) {
            Position pos = getRandomPosition();
            Tile woodLog = new Tile();
            woodLog.setPosition(pos);
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
        if (lake != null && lake.getTiles().contains(pos)) {
            return true;
        }

        if (quarry != null) {
            for (Tile tile : quarry.getTiles()) {
                if (tile.getPosition().equals(pos)) {
                    return true;
                }
            }
        }

        // TODO: Check other fixed elements...
        return false;
    }

    private Lake generateLake(int x, int y, int width, int height) {
        Lake lake = new Lake();
        ArrayList<Position> tiles = new ArrayList<>();

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                tiles.add(new Position(i, j));
            }
        }

        lake.setTiles(tiles);
        return lake;
    }

    private Cabin generateCabin(int x, int y) {
        Cabin cabin = new Cabin();
        ArrayList<Position> tiles = new ArrayList<>();

        for (int i = x; i < x + CABIN_SIZE; i++) {
            for (int j = y; j < y + CABIN_SIZE; j++) {
                tiles.add(new Position(i, j));
            }
        }

        cabin.setTiles(tiles);
        return cabin;
    }


    private Greenhouse generateGreenhouse(int x, int y, int width, int height) {
        Greenhouse greenhouse = new Greenhouse();
        ArrayList<Tile> tiles = new ArrayList<>();

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

    private Quarry generateQuarry(int x, int y, int width, int height) {
        Quarry quarry = new Quarry();
        ArrayList<Tile> tiles = new ArrayList<>();
        ArrayList<Mineral> minerals = new ArrayList<>();

        for (int i = x; i < x + width; i++) {
            for (int j = y; j < y + height; j++) {
                Tile tile = new Tile();
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

    public ArrayList<Shop> getShops() {
        return shops;
    }

    public int getMapNumber() {
        return mapNumber;
    }

    public Lake getLake() {
        return lake;
    }

    public Greenhouse getGreenhouse() {
        return greenhouse;
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

    public static ArrayList<Tile> getAllTiles() {
        return allTiles;
    }

    public void consolidateAllTiles() {
        allTiles = new ArrayList<>();

        if (cabin != null) {
            for (Position pos : cabin.getTiles()) {
                Tile tile = new Tile();
                tile.setPosition(pos);
                tile.setType(TileType.CABIN);
                allTiles.add(tile);
            }
        }

        if (greenhouse != null) {
            allTiles.addAll(greenhouse.getTiles());
        }

        if (quarry != null) {
            allTiles.addAll(quarry.getTiles());
        }

        if (lake != null) {
            for (Position pos : lake.getTiles()) {
                Tile tile = new Tile();
                tile.setPosition(pos);
                tile.setType(TileType.WATER);
                allTiles.add(tile);
            }
        }

        for (Farm farm : farms) {
            allTiles.addAll(farm.getFarmTiles());
        }

        for (Shop shop : shops) {
            allTiles.addAll(shop.getShopTiles());
        }

        for (Tree tree : trees) {
            Tile tile = new Tile();
            tile.setPosition(tree.getPosition());
            tile.setType(TileType.TREE);
            allTiles.add(tile);
        }

        for (Mineral stone : stones) {
            Tile tile = new Tile();
            tile.setPosition(stone.getPosition());
            tile.setType(TileType.STONE);
            allTiles.add(tile);
        }

        for (ForagingCrop crop : foragings) {
            Tile tile = new Tile();
            tile.setPosition(crop.getPosition());
            tile.setType(TileType.GROWING_CROP);
            allTiles.add(tile);
        }
    }
}