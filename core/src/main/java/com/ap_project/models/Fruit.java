package com.ap_project.models;

import com.ap_project.models.enums.types.FruitType;

public class Fruit extends Item {
    private final FruitType fruitType;
    private String name;

    public Fruit(FruitType fruitType) {
        this.fruitType = fruitType;
        this.name = fruitType.getName();
    }

    @Override
    public String getName() {
        return this.name;
    }

    @Override
    public String toString  () {
        return this.name;
    }
}
