package models.enums.types;

public enum AnimalProductType implements ItemType {
    CHICKEN_EGG("Chicken Egg", AnimalType.CHICKEN, 50, 800),
    LARGE_CHICKEN_EGG("Large Chicken Egg", AnimalType.CHICKEN, 95, 800),
    DUCK_EGG("Duck Egg", AnimalType.DUCK, 95, 1200),
    DUCK_FEATHER("Duck Feather", AnimalType.DUCK, 250, 1200),
    RABBIT_WOOL("Rabbit Wool", AnimalType.RABBIT, 340, 8000),
    RABBIT_FOOT("Rabbit Foot", AnimalType.RABBIT, 565, 8000),
    DINOSAUR_EGG("Dinosaur Egg", AnimalType.DINOSAUR, 350, 14000),
    COW_MILK("Cow Milk", AnimalType.COW, 125, 1500),
    LARGE_COW_MILK("Large Cow Milk", AnimalType.COW, 190, 1500),
    GOAT_MILK("Goat Milk", AnimalType.GOAT, 225, 4000),
    LARGE_GOAT_MILK("Large Goat Milk", AnimalType.GOAT, 345, 4000),
    WOOL("Wool", AnimalType.SHEEP, 340, 8000),
    TRUFFLE("Truffle", AnimalType.PIG, 625, 16000);

    private final String name;
    private final AnimalType animal;
    private final int basePrice;
    private final int purchasePrice;

    AnimalProductType(String name, AnimalType animal, int basePrice, int purchasePrice) {
        this.name = name;
        this.animal = animal;
        this.basePrice = basePrice;
        this.purchasePrice = purchasePrice;
    }

    public String getName() {
        return name;
    }

    public AnimalType getAnimal() {
        return animal;
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
