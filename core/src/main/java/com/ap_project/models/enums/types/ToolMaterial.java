package com.ap_project.models.enums.types;

public enum ToolMaterial {
    BASIC(2000, IngredientType.COPPER_BAR, 5),
    COPPER(5000, IngredientType.IRON_BAR, 5),
    IRON(10000, IngredientType.GOLD_BAR, 5),
    GOLD(25000, IngredientType.IRIDIUM_BAR, 5),
    IRIDIUM(0, null, 5);

    private final int upgradePrice;
    private final IngredientType ingredientForUpgrade;
    private final int numOfIngredientNeededForUpgrade;

    ToolMaterial(int upgradePrice, IngredientType ingredientForUpgrade, int numOfIngredientNeededForUpgrade) {
        this.upgradePrice = upgradePrice;
        this.ingredientForUpgrade = ingredientForUpgrade;
        this.numOfIngredientNeededForUpgrade = numOfIngredientNeededForUpgrade;
    }

    public int getUpgradePrice() {
        return this.upgradePrice;
    }

    public IngredientType getIngredientForUpgrade() {
        return this.ingredientForUpgrade;
    }

    public int getNumOfIngredientNeededForUpgrade() {
        return this.numOfIngredientNeededForUpgrade;
    }
}
