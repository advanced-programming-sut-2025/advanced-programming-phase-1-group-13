package models.enums.types;

import java.util.Arrays;
import java.util.List;

public enum AnimalType {
    CHICKEN(Arrays.asList(AnimalProductType.CHICKEN_EGG, AnimalProductType.LARGE_CHICKEN_EGG), true),
    DUCK(Arrays.asList(AnimalProductType.DUCK_EGG, AnimalProductType.DUCK_FEATHER), true),
    RABBIT(Arrays.asList(AnimalProductType.RABBIT_WOOL, AnimalProductType.RABBIT_FOOT), true),
    DINOSAUR(Arrays.asList(AnimalProductType.DINOSAUR_EGG), true),
    COW(Arrays.asList(AnimalProductType.COW_MILK, AnimalProductType.LARGE_COW_MILK), false),
    GOAT(Arrays.asList(AnimalProductType.GOAT_MILK, AnimalProductType.LARGE_GOAT_MILK), false),
    SHEEP(Arrays.asList(AnimalProductType.WOOL), false),
    PIG(Arrays.asList(AnimalProductType.TRUFFLE), false);

    private final List<AnimalProductType> animalProducts;
    private final boolean livesInCage;

    AnimalType(List<AnimalProductType> animalProducts, boolean livesInCage) {
        this.animalProducts = animalProducts;
        this.livesInCage = livesInCage;
    }

    public List<AnimalProductType> getAnimalProducts() {
        return animalProducts;
    }

    public boolean livesInCage() {
        return livesInCage;
    }
}
