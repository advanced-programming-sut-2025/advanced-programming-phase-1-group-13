package models;

import models.animals.Animal;
import models.enums.Quality;
import models.enums.types.AnimalProductType;

public class AnimalProduct extends Item {
    AnimalProductType type;
    int basePrice;
    Quality quality;
    Animal producerAnimal;

    public void calculatePrice() {
        this.setPrice((int) (this.basePrice * ((double) this.producerAnimal.getFriendshipLevel() / 1000 + 0.3)));
    }

    public AnimalProduct(AnimalProductType type) {
        this.type = type;
    }

    public AnimalProduct(AnimalProductType type, Animal producerAnimal, int basePrice, Quality quality) {
        this.type = type;
        this.producerAnimal = producerAnimal;
        this.basePrice = basePrice;
        this.quality = quality;
    }
}
