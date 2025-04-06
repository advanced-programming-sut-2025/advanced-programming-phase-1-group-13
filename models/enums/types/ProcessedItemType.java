package models.enums.types;

import models.AnimalProduct;
import models.Item;

import java.util.HashMap;

public enum ProcessedItemType {
    HONEY("Honey", "It's a sweet syrup produced by bees.", 75, 4, new HashMap<>(), 350),
    CHEESE("Cheese", "It's your basic cheese.", 100, 3, createIngredientsMap(new AnimalProduct(models.enums.types.AnimalProductType.COW_MILK), 1), 230),
    LARGE_CHEESE("Large Cheese", "It's your basic cheese.", 100, 3, createIngredientsMap(new AnimalProduct(models.enums.types.AnimalProductType.LARGE_COW_MILK), 1), 345),
    GOAT_CHEESE("Goat Cheese", "Soft cheese made from goat's milk.", 100, 3, createIngredientsMap(new AnimalProduct(models.enums.types.AnimalProductType.GOAT_MILK), 1), 400),
    LARGE_GOAT_CHEESE("Large Goat Cheese", "Soft cheese made from goat's milk.", 100, 3, createIngredientsMap(new AnimalProduct(models.enums.types.AnimalProductType.LARGE_GOAT_MILK), 1), 600),
//        BEER("Beer", "Drink in moderation.", 50, 24, createIngredientsMap(PlantType.WHEAT, 1), 200),
//    VINEGAR("Vinegar", "An aged fermented liquid used in many cooking recipes.", 13, 10, createIngredientsMap(ProductType.RICE, 1), 100),
//    COFFEE("Coffee", "It smells delicious. This is sure to give you a boost.", 75, 2, createIngredientsMap(ProductType.COFFEE_BEAN, 5), 150),
    // JUICE("Juice", "A sweet, nutritious beverage.", 2 * BaseIngredientEnergy, 96, createIngredientsMap(ProductType.ANY_VEGETABLE, 1), 2.25 * BaseIngredientBasePrice),
//    MEAD("Mead", "A fermented beverage made from honey. Drink in moderation.", 100, 10, createIngredientsMap(ProductType.HONEY, 1), 300),
//    PALE_ALE("Pale Ale", "Drink in moderation.", 50, 72, createIngredientsMap(ProductType.HOPS, 1), 300),
    // WINE("Wine", "Drink in moderation.", 1.75 * BaseFruitEnergy, 168, createIngredientsMap(ProductType.ANY_FRUIT, 1), 3 * FruitBasePrice),
//    DRIED_MUSHROOMS("Dried Mushrooms", "A package of gourmet mushrooms.", 50, 24, createIngredientsMap(ProductType.ANY_MUSHROOM, 5), 7.5 * MushroomBasePrice + 25),
//    DRIED_FRUIT("Dried Fruit", "Chewy pieces of dried fruit.", 75, 24, createIngredientsMap(ProductType.ANY_FRUIT_EXCEPT_GRAPES, 5), 7.5 * FruitBasePrice + 25),
//    RAISINS("Raisins", "It's said to be the Junimos' favorite food.", 125, 24, createIngredientsMap(ProductType.GRAPES, 5), 600),
//    COAL("Coal", "Turns 10 pieces of wood into one piece of coal.", "Inedible", 1, createIngredientsMap(ProductType.WOOD, 10), 50),
    CLOTH("Cloth", "A bolt of fine wool cloth.", 0, 4, createIngredientsMap(models.enums.types.AnimalProductType.WOOL, 1), 470),
    //    MAYONNAISE("Mayonnaise", "It looks spreadable.", 50, 3, createIngredientsMap(ProductType.EGG, 1), 190),
//    LARGE_MAYONNAISE("Large Mayonnaise", "It looks spreadable.", 50, 3, createIngredientsMap(ProductType.LARGE_EGG, 1), 237),
    DUCK_MAYONNAISE("Duck Mayonnaise", "It's a rich, yellow mayonnaise.", 75, 3, createIngredientsMap(models.enums.types.AnimalProductType.DUCK_EGG, 1), 375),
    DINOSAUR_MAYONNAISE("Dinosaur Mayonnaise", "It's thick and creamy, with a vivid green hue. It smells like grass and leather.", 125, 3, createIngredientsMap(models.enums.types.AnimalProductType.DINOSAUR_EGG, 1), 800),
    TRUFFLE_OIL("Truffle Oil", "A gourmet cooking ingredient.", 38, 6, createIngredientsMap(models.enums.types.AnimalProductType.TRUFFLE, 1), 1065),
//    OIL("Oil", "All-purpose cooking oil.", 13, 6, createIngredientsMap(ProductType.CORN, 1), 100),
    // PICKLES("Pickles", "A jar of your homemade pickles.", 1.75 * BaseIngredientEnergy, 6, createIngredientsMap(ProductType.ANY_VEGETABLE, 1), 2 * BasePrice + 50),
    // JELLY("Jelly", "Gooey.", 2 * BaseFruitEnergy, 72, createIngredientsMap(ProductType.ANY_FRUIT, 1), 2 * BaseFruitPrice + 50),
    // SMOKED_FISH("Smoked Fish", "A whole fish, smoked to perfection.", 1.5 * FishEnergy, 1, createIngredientsMap(ProductType.ANY_FISH, 1), 2 * FishPrice),
    // METAL_BAR("Any metal bar", "Turns ore and coal into metal bars.", "Inedible", 4, createIngredientsMap(ProductType.ANY_ORE, 5), 10 * OrePrice);
    ;

    String name;
    String description;
    int energy;
    int processingTime; // in hours
    HashMap<Item, Integer> ingredients;
    int sellPrice;

    ProcessedItemType(String name, String description, int energy, int processingTime, HashMap<Item, Integer> ingredients, int sellPrice) {
        this.name = name;
        this.description = description;
        this.energy = energy;
        this.processingTime = processingTime;
        this.ingredients = ingredients;
        this.sellPrice = sellPrice;
    }

    private static HashMap<Item, Integer> createIngredientsMap(Item item, int quantity) {
        HashMap<Item, Integer> map = new HashMap<>();
        map.put(item, quantity);
        return map;
    }

}
