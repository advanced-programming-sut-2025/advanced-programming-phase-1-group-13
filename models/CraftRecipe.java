package models;

import models.enums.types.Craft;

public class CraftRecipe {
    private String nameOfCraft;
    private int sellPrice;
    private Craft craft;

    public CraftRecipe(Craft craft) {
        this.craft = craft;
        this.nameOfCraft = craft.getName();
        this.sellPrice = switch (craft) {
            case BOMB, MEGA_BOMB, CHERRY_BOMB -> 50;
            case MYSTIC_TREE_SEED -> 100;
            default -> 0;
        };
    }
}