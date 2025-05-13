package models;

import models.enums.types.FoodBuff;
import models.enums.types.FoodType;

public class CookingRecipe extends Item {
    private int energyOfFood;
    private final FoodType foodType;
    private final String nameOfFood;
    private int sellPrice;
    private final FoodBuff buff;
    private int buffDurationInHours;

    public CookingRecipe(FoodType foodType) {
        this.foodType = foodType;
        this.nameOfFood = foodType.getName();
        this.energyOfFood = foodType.getEnergy();
        this.sellPrice = foodType.getSellPrice();
        this.buff = foodType.getBuff();
        this.buffDurationInHours = foodType.getBuff().getBuffDurationInHours();
    }


    public int getEnergyOfFood() {
        return energyOfFood;
    }

    public int getBuffDurationInHours() {
        return buffDurationInHours;
    }

    public FoodBuff getBuff() {
        return buff;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public String getNameOfFood() {
        return nameOfFood;
    }

    public FoodType getFoodType() {
        return foodType;
    }

    public static CookingRecipe getCookingRecipe(String foodName) {
        FoodType foodType = FoodType.getFoodTypeByName(foodName);
        if (foodType == null) {
            return null;
        }
        return new CookingRecipe(foodType);
    }
}