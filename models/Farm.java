package models;

import models.enums.types.ArtisanType;
import models.enums.types.FarmBuildingType;
import models.enums.types.FertilizerType;
import models.enums.types.TileType;
import models.farming.Crop;
import models.farming.Harvestable;
import models.farming.PlantSource;
import models.farming.Tree;

import java.util.ArrayList;
import java.util.List;

public class Farm {
    private ArrayList<Tile> farmTiles;
    private Cabin cabin;
    private Greenhouse greenhouse;
    private Quarry quarry;
    private ArrayList<Lake> lakes;
    private int cropCount;
    private ArrayList<Crop> plantedCrops;
    private ArrayList<Tree> trees;
    private int mapNumberToFollow;
    private ArrayList<FarmBuilding> farmBuildings;
    private int height;
    private int width;
    private ArrayList<Artisan> artisans;


    public Farm(int mapNumberToFollow) {
        this.mapNumberToFollow = mapNumberToFollow;
        this.cropCount = 0;
        this.plantedCrops = new ArrayList<>();
        this.trees = new ArrayList<>();
        this.farmBuildings = new ArrayList<>();
        this.artisans = new ArrayList<>();
        this.height = GameMap.getMAP_SIZE() / 10;
        this.width = GameMap.getMAP_SIZE() / 10;

        if (mapNumberToFollow == 1) {
            this.cabin = new Cabin();
            this.lakes = new ArrayList<>();
            this.quarry = new Quarry();
            this.farmTiles = new ArrayList<>();
            // TODO
            this.cabin = new Cabin(); // with map1 properties
            this.lakes = new ArrayList<>(); // with map1 properties
            this.quarry = new Quarry(); // with map1 properties
            this.farmTiles = new ArrayList<>(); // with map1 properties
        } else if (mapNumberToFollow == 2) {
            this.cabin = new Cabin();
            this.lakes = new ArrayList<>();
            this.quarry = new Quarry();
            this.farmTiles = new ArrayList<>();
        }
    }

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

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void placeScarecrow(Position position) {

    }

    public void plant(PlantSource seed, Position position) {
        // TODO

    }

    public void showPlant(Position position) {
        // TODO

    }

    public void fertilize(FertilizerType fertilizer, Position position) {
        // TODO
    }

    public void harvest(Harvestable harvestable) {
        // TODO
    }

    public ArrayList<Tile> getFarmTiles() {
        return this.farmTiles;
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

    public ArrayList<Lake> getLakes() {
        return this.lakes;
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
                if (!this.getTileByPosition(currentPosition).getType().equals(TileType.NOT_PLOWED_SOIL)) {
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
                AnimalLivingSpace animalLivingSpace = (AnimalLivingSpace) farmBuilding;
                if (!animalLivingSpace.isFull()) {
                    return animalLivingSpace;
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
                animal.changeFriendship(-10);
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

    public Tile getTileByPosition(Position position) {
        for (Tile tile : farmTiles) {
            if (tile.getPosition().equals(position)) {
                return tile;
            }
        }
        return null;
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