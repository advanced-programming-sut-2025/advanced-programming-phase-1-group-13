package com.ap_project.models;

import com.ap_project.models.enums.types.*;
import com.ap_project.models.farming.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Farm {
    private ArrayList<Tile> farmTiles;
    private Cabin cabin;
    private Greenhouse greenhouse;
    private Quarry quarry;
    private int cropCount;
    private final ArrayList<Crop> plantedCrops;
    private final ArrayList<Tree> trees;
    private final ArrayList<FarmBuilding> farmBuildings;
    private final int height = 74;
    private final int width = 55;
    private final ArrayList<Artisan> artisans;
    private ArrayList<ShippingBin> shippingBins;
    private final int mapNumber;
    private Lake lake;
    private final ArrayList<Mineral> stones;
    private final ArrayList<Tile> woodLogs;
    private final ArrayList<ForagingCrop> foragings;
    private final transient Random random;
    private ArrayList<Tile> allTiles;

    public Farm(int mapNumberToFollow) {
        this.cropCount = 0;
        this.plantedCrops = new ArrayList<>();
        this.trees = new ArrayList<>();
        this.farmBuildings = new ArrayList<>();
        this.artisans = new ArrayList<>();
        this.mapNumber = mapNumberToFollow;
        this.random = new Random();
        this.stones = new ArrayList<>();
        this.foragings = new ArrayList<>();
        this.woodLogs = new ArrayList<>();

        for (ArtisanType artisanType : ArtisanType.values()) {
            this.artisans.add(new Artisan(artisanType));
        }

        this.cabin = new Cabin();
        this.quarry = new Quarry();
        this.farmTiles = new ArrayList<>();

        generateBaseMapTiles();
        generateFixedElements();
        consolidateAllTiles();
        generateRandomElements();
    }

    // GameMap Methods
    private void generateBaseMapTiles() {
        allTiles = new ArrayList<>(width * height);

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
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

    private void generateFixedElements() {
        if (mapNumber == 1) {
            this.lake = generateLake(20, 35, 10, 14);
            this.cabin = generateCabin(63, 6, 9, 6);
            this.greenhouse = generateGreenhouse();
            this.quarry = generateQuarry(20, 50, 10, 10);
        } else if (mapNumber == 2) {
            this.lake = generateLake(50, 10, 6, 6);
            this.cabin = generateCabin(4, 4, 9, 6);
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
            if (tile != null) {
                Tree tree = new Tree(TreeType.getRandomTreeType(), tile);
                trees.add(tree);  // Add to trees list
                tile.setType(TileType.TREE);
                tile.pLaceItemOnTile(tree);
            }
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
            int x = random.nextInt(width);
            int y = random.nextInt(height);
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

    public int getMapNumber() {
        return mapNumber;
    }

    public Lake getLake() {
        return lake;
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

//        for (Farm farm : farms) {
//            for (Tile tile : farm.getFarmTiles()) {
//                Tile baseTile = getTileByPosition(tile.getPosition());
//                if (baseTile != null) baseTile.setType(tile.getType());
//                else allTiles.add(tile);
//            }
//        }
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

    // Farm Methods
    public Tile getTileAt(int x, int y) {
        if (x < 0 || x >= width || y < 0 || y >= height) {
            return null;
        }
        // Convert 2D coordinates to 1D index (row-major order)
        int index = y * width + x;
        if (index >= 0 && index < farmTiles.size()) {
            return farmTiles.get(index);
        }
        return null;
    }

    public void setCropCount(int cropCount) {
        this.cropCount = cropCount;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<ShippingBin> getShippingBins() {
        return shippingBins;
    }

    public ArrayList<Tile> getFarmTiles() {
        return this.getFarmTiles();
    }

    public Cabin getCabin() {
        return this.cabin;
    }

    public Greenhouse getGreenhouse() {
        return this.greenhouse;
    }

    public Quarry getQuarry() {
        return this.quarry;
    }

    public ArrayList<Crop> getPlantedCrops() {
        return this.plantedCrops;
    }

    public ArrayList<Tree> getTrees() {
        return trees;
    }

    public int getCropCount() {
        return cropCount;
    }

    public ArrayList<FarmBuilding> getFarmBuildings() {
        return farmBuildings;
    }

    public ArrayList<Artisan> getArtisans() {
        return artisans;
    }

    public void addArtisans(Artisan artisan) {
        this.artisans.add(artisan);
    }

    public boolean canPlaceBuilding(FarmBuildingType farmBuildingType, Position position) {
        int xTopLeft = position.getX();
        int yTopLeft = position.getY();
        for (int i = 0; i < farmBuildingType.getWidth(); i++) {
            for (int j = 0; j < farmBuildingType.getLength(); j++) {
                Position currentPosition = new Position(xTopLeft + i, yTopLeft + j);
                if (!App.getCurrentGame().getPlayerByUsername(App.getLoggedIn().getUsername()).getFarm().getTileByPosition(currentPosition).getType().equals(TileType.NOT_PLOWED_SOIL)) {
                    return false;
                }
            }
        }
        return true;
    }


    public ArrayList<Animal> getAllFarmAnimals() {
        ArrayList<Animal> animals = new ArrayList<>();

        for (FarmBuilding farmBuilding : this.getFarmBuildings()) {
            if (farmBuilding.getFarmBuildingType().getIsCage() != null) {
                AnimalLivingSpace animalLivingSpace = (AnimalLivingSpace) farmBuilding;
                animals.addAll(animalLivingSpace.getAnimals());
            }
        }

        return animals;
    }

    public Animal getAnimalByName(String name) {
        for (Animal animal : getAllFarmAnimals()) {
            if (animal.getName().equals(name)) {
                return animal;
            }
        }
        return null;
    }

    public AnimalLivingSpace getAvailableLivingSpace(List<FarmBuildingType> livingSpaceTypes) {
        for (FarmBuilding farmBuilding : this.getFarmBuildings()) {
            if (livingSpaceTypes.contains(farmBuilding.getFarmBuildingType())) {
                if (farmBuilding instanceof AnimalLivingSpace) {
                    AnimalLivingSpace animalLivingSpace = (AnimalLivingSpace) farmBuilding;
                    if (!animalLivingSpace.isFull()) {
                        return animalLivingSpace;
                    }
                }
            }
        }

        return null;
    }

    public String updateAnimals() {
        StringBuilder message = new StringBuilder();

        for (Animal animal : this.getAllFarmAnimals()) {
            if (!animal.hasBeenFedToday()) {
                animal.changeFriendship(-20);
                message.append(animal.getName()).append(" was not fed today.\n");
            } else if (animal.getFriendshipLevel() >= 100) {
                animal.produceProduct();
                message.append(animal.getName()).append(" produced some products today.\n");
            }

            if (!animal.hasBeenPetToday()) {
                int amount = (animal.getFriendshipLevel() / 200) - 10;
                animal.changeFriendship(amount);
                message.append(animal.getName()).append(" was not pet today.\n");
            }

            if (animal.isOutside()) {
                animal.changeFriendship(-20);
                message.append(animal.getName()).append(" slept outside last night.\n");
            }

            message.append("Your friendship level with ").append(animal.getName()).append("is now ")
                .append(animal.getFriendshipLevel()).append(".\n");
        }

        return message.toString();
    }

    public FarmBuilding getFarmBuildingByPosition(Position position) {
        for (FarmBuilding farmBuilding : this.getFarmBuildings()) {
            int xTopLeft = farmBuilding.getPositionOfUpperLeftCorner().getX();
            int yTopLeft = farmBuilding.getPositionOfUpperLeftCorner().getY();
            int length = farmBuilding.getLength();
            int width = farmBuilding.getWidth();

            int x = position.getX();
            int y = position.getY();

            if (xTopLeft < x && xTopLeft + length > x && yTopLeft < y && yTopLeft + width > y) {
                return farmBuilding;
            }
        }
        return null;
    }

    public Position getRandomTilePosition() {
        if (farmTiles.isEmpty()) {
            return null;
        }
        Random random = new Random();
        return farmTiles.get(random.nextInt(farmTiles.size())).getPosition();
    }


    public Artisan getFullArtisanByArtisanType(ArtisanType artisanType) {
        for (Artisan artisan : this.artisans) {
            if (artisan.getType().equals(artisanType) && artisan.getItemPending() != null) {
                return artisan;
            }
        }
        return null;
    }

    public Artisan getEmptyArtisanByArtisanType(ArtisanType artisanType) {
        for (Artisan artisan : this.artisans) {
            if (artisan.getType().equals(artisanType) && artisan.getItemPending() == null) {
                return artisan;
            }
        }
        return null;
    }
}
