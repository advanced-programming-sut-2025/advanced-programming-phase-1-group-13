package models;

import models.enums.types.FruitType;

public class Fruit extends Item {
    private final FruitType fruitType;

    public Fruit(FruitType fruitType) {
        this.fruitType = fruitType;
    }
}
