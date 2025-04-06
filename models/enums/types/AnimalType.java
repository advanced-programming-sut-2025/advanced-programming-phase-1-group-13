package models.enums.types;

import models.AnimalProduct;

import java.util.ArrayList;

public enum AnimalType {
    CHICKEN(null, null),
    DUCK(null, null),
    RABBIT(null, null),
    DINOSAUR(null, null),
    COW(null, null),
    GOAT(null, null),
    SHEEP(null, null),
    PIG(null, null);

    private final ArrayList<models.enums.types.AnimalProductType> animalProducts;
    private final Boolean livesInCage; // if false, lives in Stable

    AnimalType(ArrayList<AnimalProduct> animalProducts, Boolean livesInCage) {
        this.animalProducts = animalProducts;
        this.livesInCage = livesInCage;
    }
}
