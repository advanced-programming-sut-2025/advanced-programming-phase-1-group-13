package models;

import models.enums.types.CraftType;
import models.enums.types.IngredientType;

import java.util.HashMap;

public class Craft extends Item {
    private final CraftType craftType;
    private final String name;

    public Craft(CraftType craftType) {
        this.craftType = craftType;
        this.name = craftType.getName();
    }

    public HashMap<IngredientType, Integer> ingredients() {
        return this.craftType.getIngredients();
    }

    public CraftType getCraftType() {
        return this.craftType;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
