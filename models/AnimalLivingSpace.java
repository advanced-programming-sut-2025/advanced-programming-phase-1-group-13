package models;

import models.enums.Capacity;
import models.enums.types.FarmBuildingType;

import java.util.ArrayList;

public class AnimalLivingSpace extends FarmBuilding {
    private final int capacity;
    private ArrayList<Animal> animals;
    private boolean isCage; // type = Stable if false

    public AnimalLivingSpace(FarmBuildingType farmBuildingType, Position positionOfUpperLeftCorner) {
        super(farmBuildingType, positionOfUpperLeftCorner);
        this.capacity = farmBuildingType.getCapacity();
    }

    public int getCapacity() {
        return capacity;
    }

    public ArrayList<Animal> getAnimals() {
        return animals;
    }

    public boolean isCage() {
        return isCage;
    }

    public boolean isFull() {
        return this.animals.size() == capacity;
    }

    void addAnimal(Animal animal) {
        // TODO
    }

    void removeAnimal(Animal animal) {
        // TODO
    }
}
