package models;

import models.enums.Quality;
import models.enums.types.ProductType;

public class AnimalProduct extends Item {
    ProductType type;
    int basePrice;
    Quality quality;
    Animal producerAnimal;

    public void calculatePrice() {
        this.setPrice((int) (this.basePrice * ((double) this.producerAnimal.getFriendshipLevel() / 1000 + 0.3)));
    }

    public AnimalProduct(ProductType type) {
        this.type = type;
    }

    public AnimalProduct(ProductType type, Animal producerAnimal, int basePrice, Quality quality) {
        this.type = type;
        this.producerAnimal = producerAnimal;
        this.basePrice = basePrice;
        this.quality = quality;
    }
}
