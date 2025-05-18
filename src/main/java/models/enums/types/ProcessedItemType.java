package models.enums.types;

import models.*;
import models.farming.Crop;

import java.util.ArrayList;
import java.util.HashMap;

public enum ProcessedItemType implements ItemType {
    HONEY("Honey", "It's a sweet syrup produced by bees.", 75, 4,
            new HashMap<>(), 350),
    CHEESE("Cheese", "It's your basic cheese.", 100, 3,
            createIngredientsMap(AnimalProductType.COW_MILK, 1), 230),
    LARGE_CHEESE("Large Cheese", "It's your basic cheese.", 100, 3,
            createIngredientsMap(AnimalProductType.LARGE_COW_MILK, 1), 345),
    GOAT_CHEESE("Goat Cheese", "Soft cheese made from goat's milk.", 100, 3,
            createIngredientsMap(AnimalProductType.GOAT_MILK, 1), 400),
    LARGE_GOAT_CHEESE("Large Goat Cheese", "Soft cheese made from goat's milk.", 100, 3,
            createIngredientsMap(AnimalProductType.LARGE_GOAT_MILK, 1), 600),
    BEER("Beer", "Drink in moderation.", 50, 24,
            createIngredientsMap(CropType.WHEAT, 1), 200),
    VINEGAR("Vinegar", "An aged fermented liquid used in many cooking recipes.", 13, 10,
            createIngredientsMap(CropType.UNMILLED_RICE, 1), 100),
    COFFEE("Coffee", "It smells delicious. This is sure to give you a boost.", 75, 2,
            createIngredientsMap(CropType.COFFEE_BEAN, 5), 150),
    JUICE("Juice", "A sweet, nutritious beverage.", 0, 96, null, 0), // TODO
    MEAD("Mead", "A fermented beverage made from honey. Drink in moderation.", 100, 10,
            createIngredientsMap(ProcessedItemType.HONEY, 1), 300),
    PALE_ALE("Pale Ale", "Drink in moderation.", 50, 72,
            createIngredientsMap(CropType.HOPS, 1), 300),
    WINE("Wine", "Drink in moderation.", 0, 168, null, 0), // TODO
    DRIED_MUSHROOMS("Dried Mushrooms", "A package of gourmet mushrooms.", 50, 24, null, 0), // TODO
    DRIED_FRUIT("Dried Fruit", "Chewy pieces of dried fruit.", 75, 24, null, 0), // TODO
    RAISINS("Raisins", "It's said to be the Junimos' favorite food.", 125, 24,
            createIngredientsMap(CropType.GRAPE, 5), 600),
    COAL("Coal", "Turns 10 pieces of wood into one piece of coal.", 0, 1,
            createIngredientsMap(MaterialType.WOOD, 10), 50),
    CLOTH("Cloth", "A bolt of fine wool cloth.", 0, 4,
            createIngredientsMap(AnimalProductType.WOOL, 1), 470),
    MAYONNAISE("Mayonnaise", "It looks spreadable.", 50, 3,
            createIngredientsMap(AnimalProductType.CHICKEN_EGG, 1), 190),
    LARGE_MAYONNAISE("Large Mayonnaise", "It looks spreadable.", 50, 3,
            createIngredientsMap(AnimalProductType.CHICKEN_EGG, 1), 237),
    DUCK_MAYONNAISE("Duck Mayonnaise", "It's a rich, yellow mayonnaise.", 75, 3,
            createIngredientsMap(AnimalProductType.DUCK_EGG, 1), 375),
    DINOSAUR_MAYONNAISE("Dinosaur Mayonnaise",
            "It's thick and creamy, with a vivid green hue. It smells like grass and leather.", 125, 3,
            createIngredientsMap(AnimalProductType.DINOSAUR_EGG, 1), 800),
    TRUFFLE_OIL("Truffle Oil", "A gourmet cooking ingredient.", 38, 6,
            createIngredientsMap(AnimalProductType.TRUFFLE, 1), 1065),
    OIL("Oil", "All-purpose cooking oil.", 13, 6,
            createIngredientsMap(CropType.CORN, 1), 100),
    PICKLES("Pickles", "A jar of your homemade pickles.", 0, 6, null, 0), // TODO
    JELLY("Jelly", "Gooey.", 0, 72, null, 0), // TODO
    SMOKED_FISH("Smoked Fish", "A whole fish, smoked to perfection.", 0, 1,
            null, 0), // TODO
    METAL_BAR("Any metal bar", "Turns ore and coal into metal bars.", 0, 4,
            null, 10 * 0); // TODO

    final String name;
    final String description;
    final int energy;
    final int processingTime; // in hours
    final HashMap<ItemType, Integer> ingredients;
    final double sellPrice;

    ProcessedItemType(String name, String description, int energy, int processingTime, HashMap<ItemType, Integer> ingredients, double sellPrice) {
        this.name = name;
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
    }

    public String getDescription() {
        return description;
    }

    public int getEnergy() {
        return energy;
    }

    public int getProcessingTime() {
        return processingTime;
    }

    public HashMap<ItemType, Integer> getIngredients() {
        return ingredients;
    }

    public double getSellPrice() {
        return sellPrice;
    }

    @Override
    public String getName() {
        return this.name;
    }

    private static HashMap<ItemType, Integer> createIngredientsMap(ItemType item, int quantity) {
        HashMap<ItemType, Integer> map = new HashMap<>();
        map.put(item, quantity);
        return map;
    }

    private double calculateJuicePrice(FruitType fruitType) {
        return 2.25 * fruitType.getPrice();
    }

    private double calculateWinePrice(FruitType fruitType) {
        return 3 * fruitType.getPrice();
    }

    private double calculateDriedMushroomPrice(MushroomTypes mushroomType) {
        return 7.5 * mushroomType.getPrice() + 25;
    }

    private double calculateDriedFruitPrice(FruitType fruitType) {
        return 7.5 * fruitType.getPrice() + 25;
    }

    private double calculatePicklePrice(VegetableType vegetableType) {
        return 7.5 * vegetableType.getPrice() + 25;
    }

    public static ProcessedItemType getProcessedItemTypeByName(String name) {
        for (ProcessedItemType processedItemType : ProcessedItemType.values()) {
            if (processedItemType.getName().equals(name)) {
                return processedItemType;
            }
        }
        return null;
    }

    public static ProcessedItemType getProcessedItemTypeByIngredients(ArrayList<ItemType> ingredients, ArtisanType artisanType1) {
        HashMap<ItemType, Integer> ingredientMap = new HashMap<>();
        for (ItemType item : ingredients) {
            ingredientMap.put(item, 1);
        }

        for (ProcessedItemType processedItemType : ProcessedItemType.values()) {
            ArtisanType artisanType2 = processedItemType.getArtisanType();
            if (artisanType2 == null) {
                continue;
            }

            if (artisanType2.equals(artisanType1)) {
                if (processedItemType.getIngredients().equals(ingredientMap)) {
                    return processedItemType;
                }
            }
        }
        return null;
    }

    public ArtisanType getArtisanType() {
        for (ArtisanType artisanType : ArtisanType.values()) {
            if (artisanType.getItemsTheArtisanProduces().contains(this)) {
                return artisanType;
            }
        }
        return null;
    }
}