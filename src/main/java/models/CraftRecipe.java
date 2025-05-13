package models;

import models.enums.types.CraftType;

public class CraftRecipe {
    private final String nameOfCraft;
    private int sellPrice;
    private final CraftType craftType;

    public CraftRecipe(CraftType craftType) {
        this.craftType = craftType;
        this.nameOfCraft = craftType.getName();
        this.sellPrice = switch (craftType) {
            case BOMB, MEGA_BOMB, CHERRY_BOMB -> 50;
            case MYSTIC_TREE_SEED -> 100;
            default -> 0;
        };
    }
}