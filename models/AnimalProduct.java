package models;

import models.animals.Animal;
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
}
