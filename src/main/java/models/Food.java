package models;

import models.enums.types.FoodType;

public class Food extends Item {
    private final FoodType foodType;
    private final CookingRecipe cookingRecipe;
    private int energy;
    private String name;

    public Food(CookingRecipe cookingRecipe) {
        this.cookingRecipe = cookingRecipe;
        this.foodType = cookingRecipe.getFoodType();
        this.energy = foodType.getEnergy();
        this.name = foodType.getName();
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

    @Override
    public String getName() {
        return this.name;
    }
}
