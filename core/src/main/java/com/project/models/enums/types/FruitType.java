package com.project.models.enums.types;

public enum FruitType implements ItemType {
    APRICOT("Apricot", 59),
    CHERRY("Cherry", 80),
    BANANA("Banana", 150),
    MANGO("Mango", 130),
    ORANGE("Orange", 100),
    PEACH("Peach", 140),
    APPLE("Apple", 100),
    POMEGRANATE("Pomegranate", 140),

    OAK_RESIN("Oak Resin", 150),
    MAPLE_SYRUP("Maple Syrup", 200),
    PINE_TAR("Pine Tar", 100),
    SAP("Sap", 2),
    COMMON_MUSHROOM("Common Mushroom", 40),
    MYSTIC_SYRUP("Mystic Syrup", 1000);

    private final String name;
    private final int price;

    FruitType(String name, int price) {
        this.name = name;
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
