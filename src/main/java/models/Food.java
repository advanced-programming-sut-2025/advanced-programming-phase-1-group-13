package models;

import models.enums.types.FoodType;

public class Food extends Item {
    private final FoodType foodType;
    private final CookingRecipe cookingRecipe;

    public Food(CookingRecipe cookingRecipe) {
        this.cookingRecipe = cookingRecipe;
        this.foodType = cookingRecipe.getFoodType();
    }

    public Food(FoodType foodType) {
        this.foodType = foodType;
        this.cookingRecipe = new CookingRecipe(foodType);
    }

}
