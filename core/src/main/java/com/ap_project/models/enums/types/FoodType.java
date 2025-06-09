package com.project.models.enums.types;

import java.util.HashMap;

public enum FoodType implements ItemType {
    FRIED_EGG("Fried Egg", createIngredientsMap(IngredientType.EGG, 1), 50, FoodBuff.NOTHING, "Starter", 35),
    BAKED_FISH("Baked Fish", createIngredientsMap(IngredientType.SARDINE, 1, IngredientType.SALMON, 1, IngredientType.WHEAT, 1), 75, FoodBuff.NOTHING, "Starter", 100),
    SALAD("Salad", createIngredientsMap(IngredientType.LEEK, 1, IngredientType.DANDELION, 1), 113, FoodBuff.NOTHING, "Starter", 110),
    OMELET("Omelet", createIngredientsMap(IngredientType.EGG, 1, IngredientType.MILK, 1), 100, FoodBuff.NOTHING, "Stardrop Saloon", 125),
    PUMPKIN_PIE("Pumpkin Pie", createIngredientsMap(IngredientType.PUMPKIN, 1, IngredientType.WHEAT_FLOUR, 1, IngredientType.MILK, 1, IngredientType.SUGAR, 1), 225, FoodBuff.NOTHING, "Stardrop Saloon", 385),
    SPAGHETTI("Spaghetti", createIngredientsMap(IngredientType.WHEAT_FLOUR, 1, IngredientType.TOMATO, 1), 75, FoodBuff.NOTHING, "Stardrop Saloon", 120),
    PIZZA("Pizza", createIngredientsMap(IngredientType.WHEAT_FLOUR, 1, IngredientType.TOMATO, 1, IngredientType.CHEESE, 1), 150, FoodBuff.NOTHING, "Stardrop Saloon", 300),
    TORTILLA("Tortilla", createIngredientsMap(IngredientType.CORN, 1), 50, FoodBuff.NOTHING, "Stardrop Saloon", 50),
    MAKI_ROLL("Maki Roll", createIngredientsMap(IngredientType.ANY_FISH, 1, IngredientType.RICE, 1, IngredientType.FIBER, 1), 100, FoodBuff.NOTHING, "Stardrop Saloon", 220),
    TRIPLE_SHOT_ESPRESSO("Triple Shot Espresso", createIngredientsMap(IngredientType.COFFEE, 3), 200, FoodBuff.MAX_ENERGY_PLUS_100, "Stardrop Saloon", 450),
    COOKIE("Cookie", createIngredientsMap(IngredientType.WHEAT_FLOUR, 1, IngredientType.SUGAR, 1, IngredientType.EGG, 1), 90, FoodBuff.NOTHING, "Stardrop Saloon", 140),
    HASH_BROWNS("Hash Browns", createIngredientsMap(IngredientType.POTATO, 1, IngredientType.OIL, 1), 90, FoodBuff.FARMING_5_HOURS, "Stardrop Saloon", 120),
    PANCAKES("Pancakes", createIngredientsMap(IngredientType.WHEAT_FLOUR, 1, IngredientType.EGG, 1), 90, FoodBuff.FORAGING_11_HOURS, "Stardrop Saloon", 80),
    FRUIT_SALAD("Fruit Salad", createIngredientsMap(IngredientType.BLUEBERRY, 1, IngredientType.MELON, 1, IngredientType.APRICOT, 1), 263, FoodBuff.NOTHING, "Stardrop Saloon", 450),
    RED_PLATE("Red Plate", createIngredientsMap(IngredientType.RED_CABBAGE, 1, IngredientType.RADISH, 1), 240, FoodBuff.MAX_ENERGY_PLUS_50, "Stardrop Saloon", 400),
    BREAD("Bread", createIngredientsMap(IngredientType.WHEAT_FLOUR, 1), 50, FoodBuff.NOTHING, "Stardrop Saloon", 60),
    SALMON_DINNER("Salmon Dinner", createIngredientsMap(IngredientType.SALMON, 1, IngredientType.AMARANTH, 1, IngredientType.KALE, 1), 125, FoodBuff.NOTHING, "Leah Reward", 300),
    VEGETABLE_MEDLEY("Vegetable Medley", createIngredientsMap(IngredientType.TOMATO, 1, IngredientType.BEET, 1), 165, FoodBuff.NOTHING, "Foraging Level 2", 120),
    FARMERS_LUNCH("Farmer's Lunch", createIngredientsMap(IngredientType.OMELET, 1, IngredientType.PARSNIP, 1), 200, FoodBuff.FARMING_5_HOURS, "Farming level 1", 150),
    SURVIVAL_BURGER("Survival Burger", createIngredientsMap(IngredientType.BREAD, 1, IngredientType.CARROT, 1, IngredientType.EGGPLANT, 1), 125, FoodBuff.FORAGING_5_HOURS, "Foraging level 3", 180),
    DISH_O_THE_SEA("Dish O' the Sea", createIngredientsMap(IngredientType.SARDINE, 2, IngredientType.HASH_BROWNS, 1), 150, FoodBuff.FISHING_5_HOURS, "Fishing level 2", 220),
    SEAFORM_PUDDING("Seafoam Pudding", createIngredientsMap(IngredientType.FLOUNDER, 1, IngredientType.MIDNIGHT_CARP, 1), 175, FoodBuff.FISHING_10_HOURS, "Fishing level 3", 300),
    MINERS_TREAT("Miner's Treat", createIngredientsMap(IngredientType.CARROT, 2, IngredientType.SUGAR, 1, IngredientType.MILK, 1), 125, FoodBuff.MINING_5_HOURS, "Mining level 1", 200);

    private final String name;
    private final HashMap<IngredientType, Integer> ingredients;
    private final int energy;
    private final FoodBuff buff;
    private final String source;
    private final int sellPrice;

    FoodType(String name,
             HashMap<IngredientType, Integer> ingredients,
             int energy,
             FoodBuff buff,
             String source,
             int sellPrice) {
        this.name = name;
        this.ingredients = ingredients;
        this.energy = energy;
        this.buff = buff;
        this.source = source;
        this.sellPrice = sellPrice;
    }

    public static FoodType getFoodTypeByName(String foodTypeName) {
        return switch (foodTypeName.toLowerCase()) {
            case "fried egg" -> FRIED_EGG;
            case "baked fish" -> BAKED_FISH;
            case "salad" -> SALAD;
            case "omelet" -> OMELET;
            case "pumpkin pie" -> PUMPKIN_PIE;
            case "spaghetti" -> SPAGHETTI;
            case "pizza" -> PIZZA;
            case "tortilla" -> TORTILLA;
            case "maki roll" -> MAKI_ROLL;
            case "triple shot espresso" -> TRIPLE_SHOT_ESPRESSO;
            case "cookie" -> COOKIE;
            case "hash browns" -> HASH_BROWNS;
            case "pancakes" -> PANCAKES;
            case "fruit salad" -> FRUIT_SALAD;
            case "red plate" -> RED_PLATE;
            case "bread" -> BREAD;
            case "salmon dinner" -> SALMON_DINNER;
            case "vegetable medley" -> VEGETABLE_MEDLEY;
            case "farmer's lunch" -> FARMERS_LUNCH;
            case "survival burger" -> SURVIVAL_BURGER;
            case "dish o' the sea" -> DISH_O_THE_SEA;
            case "seafoam pudding" -> SEAFORM_PUDDING;
            case "miner's treat" -> MINERS_TREAT;
            default -> null;
        };
    }

    @Override
    public String getName() {
        return this.name;
    }

    public HashMap<IngredientType, Integer> getIngredients() {
        return ingredients;
    }

    public int getEnergy() {
        return energy;
    }

    public FoodBuff getBuff() {
        return buff;
    }

    public String getSource() {
        return source;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public static HashMap<IngredientType, Integer> createIngredientsMap(Object... items) {
        HashMap<IngredientType, Integer> map = new HashMap<>();
        for (int i = 0; i < items.length; i += 2) {
            if (items[i] instanceof IngredientType && items[i + 1] instanceof Integer) {
                map.put((IngredientType) items[i], (Integer) items[i + 1]);
            }
        }
        return map;
    }
}
