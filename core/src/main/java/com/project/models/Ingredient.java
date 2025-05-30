package com.project.models;

import com.project.models.enums.types.IngredientType;
import com.project.models.enums.types.ItemType;

public class Ingredient extends Item {
    private final IngredientType ingredientType;
    private String name;

    public Ingredient(IngredientType ingredientType) {
        this.ingredientType = ingredientType;
        this.name = ingredientType.getName();
    }

    public IngredientType getIngredientType() {
        return this.ingredientType;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
