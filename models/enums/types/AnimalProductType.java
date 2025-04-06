package models.enums.types;

public enum AnimalProductType {
    CHICKEN_EGG(50, 800),
    LARGE_CHICKEN_EGG(95, 800),
    DUCK_EGG(95, 1200),
    DUCK_FEATHER(250, 1200),
    RABBIT_WOOL(340, 8000),
    RABBIT_FOOT(565, 8000),
    DINOSAUR_EGG(350, 14000),
    COW_MILK(125, 1500),
    LARGE_COW_MILK(190, 1500),
    GOAT_MILK(225, 4000),
    LARGE_GOAT_MILK(345, 4000),
    WOOL(340, 8000),
    TRUFFLE(625, 16000);
    
    private final int basePrice;
    private final int purchasePrice;

    AnimalProductType(int basePrice, int purchasePrice) {
        this.basePrice = basePrice;
        this.purchasePrice = purchasePrice;
    }
}
