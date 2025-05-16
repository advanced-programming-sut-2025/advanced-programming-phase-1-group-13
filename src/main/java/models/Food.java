package models;

import models.enums.types.FoodType;

public class Food extends Item {
    private final FoodType foodType;
    private final CookingRecipe cookingRecipe;
    private int energy;

    public Food(CookingRecipe cookingRecipe) {
        this.cookingRecipe = cookingRecipe;
        this.foodType = cookingRecipe.getFoodType();
        this.energy = foodType.getEnergy();
    }

    public Food(FoodType foodType) {
        this.foodType = foodType;
        this.cookingRecipe = new CookingRecipe(foodType);
    }

    public FoodType getFoodType() {
        return this.foodType;
    }

    public CookingRecipe getCookingRecipe() {
        return this.cookingRecipe;
    }

    public int getEnergy() {
        return this.energy;
    }

}
