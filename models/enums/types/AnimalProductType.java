package models.enums.types;

public enum AnimalProductType {
    CHICKEN_EGG(1),
    LARGE_CHICKEN_EGG(1),
    DUCK_EGG(1),
    DUCK_FEATHER(1),
    RABBIT_WOOL(1),
    RABBIT_FOOT(1),
    DINOSAUR_EGG(1),
    COW_MILK(1),
    LARGE_COW_MILK(1),
    GOAT_MILK(1),
    LARGE_GOAT_MILK(1),
    WOOL(1),
    TRUFFLE(1);
    
    private final int basePrice;

    AnimalProductType(int basePrice) {
        this.basePrice = basePrice;
    }
}
