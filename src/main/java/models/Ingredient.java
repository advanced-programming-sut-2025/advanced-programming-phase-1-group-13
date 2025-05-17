package models;

import models.enums.types.IngredientType;
import models.enums.types.ItemType;

public class Ingredient extends Item {
    private final IngredientType ingredientType;

    public Ingredient(IngredientType ingredientType) {
        this.ingredientType = ingredientType;
    }

    public IngredientType getIngredientType() {
        return this.ingredientType;
    }
}
