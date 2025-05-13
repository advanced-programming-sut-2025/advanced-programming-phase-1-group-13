package models.enums.types;

public enum AnimalProductType implements ItemType {
    CHICKEN_EGG("Chicken Egg", "CHICKEN", 50, 800),
    LARGE_CHICKEN_EGG("Large Chicken Egg", "CHICKEN", 95, 800),
    DUCK_EGG("Duck Egg", "DUCK", 95, 1200),
    DUCK_FEATHER("Duck Feather", "DUCK", 250, 1200),
    RABBIT_WOOL("Rabbit Wool", "RABBIT", 340, 8000),
    RABBIT_FOOT("Rabbit Foot", "RABBIT", 565, 8000),
    DINOSAUR_EGG("Dinosaur Egg", "DINOSAUR", 350, 14000),
    COW_MILK("Cow Milk", "COW", 125, 1500),
    LARGE_COW_MILK("Large Cow Milk", "COW", 190, 1500),
    GOAT_MILK("Goat Milk", "GOAT", 225, 4000),
    LARGE_GOAT_MILK("Large Goat Milk", "GOAT", 345, 4000),
    WOOL("Wool", "SHEEP", 340, 8000),
    TRUFFLE("Truffle", "PIG", 625, 16000);

    private final String name;
    private final String animalName;
    private final int basePrice;
    private final int purchasePrice;

    AnimalProductType(String name, String animalName, int basePrice, int purchasePrice) {
        this.name = name;
        this.animalName = animalName;
        this.basePrice = basePrice;
        this.purchasePrice = purchasePrice;
    }

    public String getName() {
        return name;
    }

    public AnimalType getAnimal() {
        return AnimalType.valueOf(animalName); // Lazy resolution to avoid static init errors
    }

    public int getBasePrice() {
        return basePrice;
    }

    public int getPurchasePrice() {
        return purchasePrice;
    }

    public static AnimalProductType getAnimalProductTypeByName(String name) {
        for (AnimalProductType animalProductType : AnimalProductType.values()) {
            if (animalProductType.getName().equals(name)) {
                return animalProductType;
            }
        }
        return null;
    }
}
