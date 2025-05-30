package com.project.models.enums.types;

public enum MoneyType implements ItemType {
    COIN("Coin"),
    GOLD_COIN("Gold Coin");;

    private final String name;

    MoneyType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
