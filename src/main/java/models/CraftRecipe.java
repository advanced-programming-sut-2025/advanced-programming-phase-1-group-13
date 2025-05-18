package models;

import models.enums.types.CraftType;

// todo: extends Good?
public class CraftRecipe extends Item {
    private final String nameOfCraft;
    private int sellPrice;
    private final CraftType craftType;
    private final String description;

    public CraftRecipe(CraftType craftType) {
        this.craftType = craftType;
        this.nameOfCraft = craftType.getName();
        this.sellPrice = switch (craftType) {
            case BOMB, MEGA_BOMB, CHERRY_BOMB -> 50;
            case MYSTIC_TREE_SEED -> 100;
            default -> 0;
        };
        this.description = craftType.getDescription();
    }

    public String getNameOfCraft() {
        return nameOfCraft;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
    }

    public CraftType getCraftType() {
        return craftType;
    }

    @Override
    public String toString() {
        return nameOfCraft + "\n\t(" + description + ")";
    }

    @Override
    public String getName() {
        return this.nameOfCraft;
    }
}