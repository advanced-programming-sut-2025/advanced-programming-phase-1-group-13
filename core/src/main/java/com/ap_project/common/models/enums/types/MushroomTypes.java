package com.ap_project.common.models.enums.types;

public enum MushroomTypes {
    COMMON_MUSHROOM(40),
    RED_MUSHROOM(75),
    PURPLE_MUSHROOM(90);

    private int price;

    MushroomTypes(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
