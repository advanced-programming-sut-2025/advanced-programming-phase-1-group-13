package com.ap_project.common.models.enums.types;

public enum ToolMaterial {
    BASIC("Basic", 2000, IngredientType.COPPER_BAR, 5),
    COPPER("Copper", 5000, IngredientType.IRON_BAR, 5),
    IRON_ORE("Iron Ore", 10000, IngredientType.GOLD_BAR, 5),
    GOLD("Gold", 25000, IngredientType.IRIDIUM_BAR, 5),
    IRIDIUM("Iridium", 0, null, 5);

    private final String name;
    private final int upgradePrice;
    private final IngredientType ingredientForUpgrade;
    private final int numOfIngredientNeededForUpgrade;

    ToolMaterial(String name, int upgradePrice, IngredientType ingredientForUpgrade, int numOfIngredientNeededForUpgrade) {
        this.name = name;
        this.upgradePrice = upgradePrice;
        this.ingredientForUpgrade = ingredientForUpgrade;
        this.numOfIngredientNeededForUpgrade = numOfIngredientNeededForUpgrade;
    }

    public String getName() {
        return name;
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
