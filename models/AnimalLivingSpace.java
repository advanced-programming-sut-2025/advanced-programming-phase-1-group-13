package models;

import models.enums.Capacity;
import models.enums.types.FarmBuildingType;

import java.util.ArrayList;

public class AnimalLivingSpace extends FarmBuilding {
    private final Capacity capacity;
    private ArrayList<Animal> animals;
    private boolean isCage; // type = Stable if false

    public AnimalLivingSpace(FarmBuildingType farmBuildingType, Position positionOfUpperLeftCorner, Capacity capacity) {
        super(farmBuildingType, positionOfUpperLeftCorner);
        this.capacity = capacity;
    }

    void addAnimal(Animal animal) {
        // TODO
    }

    void removeAnimal(Animal animal) {
        // TODO
    }
}
