package models.enums.types;

public enum AnimalProductType implements ItemType {
    CHICKEN_EGG("Chicken Egg", 50, 800),
    LARGE_CHICKEN_EGG("Large Chicken Egg", 95, 800),
    DUCK_EGG("Duck Egg", 95, 1200),
    DUCK_FEATHER("Duck Feather", 250, 1200),
    RABBIT_WOOL("Rabbit Wool", 340, 8000),
    RABBIT_FOOT("Rabbit Foot", 565, 8000),
    DINOSAUR_EGG("Dinosaur Egg", 350, 14000),
    COW_MILK("Cow Milk", 125, 1500),
    LARGE_COW_MILK("Large Cow Milk", 190, 1500),
    GOAT_MILK("Goat Milk", 225, 4000),
    LARGE_GOAT_MILK("Large Goat Milk", 345, 4000),
    WOOL("Wool", 340, 8000),
    TRUFFLE("Truffle", 625, 16000);

    private final String name;
    private final int basePrice;
    private final int purchasePrice;

    AnimalProductType(String name, int basePrice, int purchasePrice) {
        this.name = name;
        this.basePrice = basePrice;
        this.purchasePrice = purchasePrice;
    }

    public String getName() {
        return name;
    }

    public int getBasePrice() {
        return basePrice;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }
}
