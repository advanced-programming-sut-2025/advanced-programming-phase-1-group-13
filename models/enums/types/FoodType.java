package models.enums.types;

import java.util.HashMap;

public enum FoodType implements ItemType {
    FRIED_EGG("Fried Egg", createIngredientsMap(IngredientType.EGG, 1), 50, "", "Starter", 35),
    BAKED_FISH("Baked Fish", createIngredientsMap(IngredientType.SARDINE, 1, IngredientType.SALMON, 1, IngredientType.WHEAT, 1), 75, "", "Starter", 100),
    SALAD("Salad", createIngredientsMap(IngredientType.LEEK, 1, IngredientType.DANDELION, 1), 113, "", "Starter", 110),
    OMELET("Omelet", createIngredientsMap(IngredientType.EGG, 1, IngredientType.MILK, 1), 100, "", "Stardrop Saloon", 125),
    PUMPKIN_PIE("Pumpkin Pie", createIngredientsMap(IngredientType.PUMPKIN, 1, IngredientType.WHEAT_FLOUR, 1, IngredientType.MILK, 1, IngredientType.SUGAR, 1), 225, "", "Stardrop Saloon", 385),
    SPAGHETTI("Spaghetti", createIngredientsMap(IngredientType.WHEAT_FLOUR, 1, IngredientType.TOMATO, 1), 75, "", "Stardrop Saloon", 120),
    PIZZA("Pizza", createIngredientsMap(IngredientType.WHEAT_FLOUR, 1, IngredientType.TOMATO, 1, IngredientType.CHEESE, 1), 150, "", "Stardrop Saloon", 300),
    TORTILLA("Tortilla", createIngredientsMap(IngredientType.CORN, 1), 50, "", "Stardrop Saloon", 50),
    MAKI_ROLL("Maki Roll", createIngredientsMap(IngredientType.ANY_FISH, 1, IngredientType.RICE, 1, IngredientType.FIBER, 1), 100, "", "Stardrop Saloon", 220),
    TRIPLE_SHOT_ESPRESSO("Triple Shot Espresso", createIngredientsMap(IngredientType.COFFEE, 3), 200, "Max Energy + 100 (5 hours)", "Stardrop Saloon", 450),
    COOKIE("Cookie", createIngredientsMap(IngredientType.WHEAT_FLOUR, 1, IngredientType.SUGAR, 1, IngredientType.EGG, 1), 90, "", "Stardrop Saloon", 140),
    HASH_BROWNS("Hash Browns", createIngredientsMap(IngredientType.POTATO, 1, IngredientType.OIL, 1), 90, "Farming (5 hours)", "Stardrop Saloon", 120),
    PANCAKES("Pancakes", createIngredientsMap(IngredientType.WHEAT_FLOUR, 1, IngredientType.EGG, 1), 90, "Foraging (11 hours)", "Stardrop Saloon", 80),
    FRUIT_SALAD("Fruit Salad", createIngredientsMap(IngredientType.BLUEBERRY, 1, IngredientType.MELON, 1, IngredientType.APRICOT, 1), 263, "", "Stardrop Saloon", 450),
    RED_PLATE("Red Plate", createIngredientsMap(IngredientType.RED_CABBAGE, 1, IngredientType.RADISH, 1), 240, "Max Energy +50 (3 hours)", "Stardrop Saloon", 400),
    BREAD("Bread", createIngredientsMap(IngredientType.WHEAT_FLOUR, 1), 50, "", "Stardrop Saloon", 60),
    SALMON_DINNER("Salmon Dinner", createIngredientsMap(IngredientType.SALMON, 1, IngredientType.AMARANTH, 1, IngredientType.KALE, 1), 125, "", "Leah Reward", 300),
    VEGETABLE_MEDLEY("Vegetable Medley", createIngredientsMap(IngredientType.TOMATO, 1, IngredientType.BEET, 1), 165, "", "Foraging Level 2", 120),
    FARMERS_LUNCH("Farmer's Lunch", createIngredientsMap(IngredientType.OMELET, 1, IngredientType.PARSNIP, 1), 200, "Farming (5 hours)", "Farming level 1", 150),
    SURVIVAL_BURGER("Survival Burger", createIngredientsMap(IngredientType.BREAD, 1, IngredientType.CARROT, 1, IngredientType.EGGPLANT, 1), 125, "Foraging (5 hours)", "Foraging level 3", 180),
    DISH_O_THE_SEA("Dish O' the Sea", createIngredientsMap(IngredientType.SARDINE, 2, IngredientType.HASH_BROWNS, 1), 150, "Fishing (5 hours)", "Fishing level 2", 220),
    SEAFORM_PUDDING("Seafoam Pudding", createIngredientsMap(IngredientType.FLOUNDER, 1, IngredientType.MIDNIGHT_CARP, 1), 175, "Fishing (10 hours)", "Fishing level 3", 300),
    MINERS_TREAT("Miner's Treat", createIngredientsMap(IngredientType.CARROT, 2, IngredientType.SUGAR, 1, IngredientType.MILK, 1), 125, "Mining (5 hours)", "Mining level 1", 200);

    private final String name;
    private final HashMap<IngredientType, Integer> ingredients;
    private final int energy;
    private final String buff;
    private final String source;
    private final int sellPrice;

    FoodType(String name, HashMap<IngredientType, Integer> ingredients, int energy, String buff, String source, int sellPrice) {
        this.name = name;
        this.ingredients = ingredients;
        this.energy = energy;
        this.buff = buff;
        this.source = source;
        this.sellPrice = sellPrice;
    }

    public String getName() {
        return this.name;
    }

    public HashMap<IngredientType, Integer> getIngredients() {
        return ingredients;
    }

    public int getEnergy() {
        return energy;
    }

    public String getBuff() {
        return buff;
    }

    public String getSource() {
        return source;
    }

    public int getSellPrice() {
        return sellPrice;
    }

    private static HashMap<IngredientType, Integer> createIngredientsMap(Object... items) {
        HashMap<IngredientType, Integer> map = new HashMap<>();
        for (int i = 0; i < items.length; i += 2) {
            if (items[i] instanceof IngredientType && items[i + 1] instanceof Integer) {
                map.put((IngredientType) items[i], (Integer) items[i + 1]);
            }
        }
        return map;
    }
}
