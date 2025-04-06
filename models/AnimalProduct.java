package models;

import models.enums.Quality;
import models.enums.types.AnimalProductType;

public class AnimalProduct extends Item {
    private AnimalProductType type;
    private int basePrice;
    private Quality quality;
    private Animal producerAnimal;

    public void calculatePrice() {
        this.setPrice((int) (this.basePrice * ((double) this.producerAnimal.getFriendshipLevel() / 1000 + 0.3)));
    }

    public AnimalProduct(models.enums.types.AnimalProductType type) {
        this.type = type;
    }

    public AnimalProduct(models.enums.types.AnimalProductType type, Animal producerAnimal, int basePrice, Quality quality) {
        this.type = type;
        this.producerAnimal = producerAnimal;
        this.basePrice = basePrice;
        this.quality = quality;
    }

    public AnimalProductType getType() {
        return this.type;
    }

    public Quality getQuality() {
        return this.quality;
    }

    public int getBasePrice() {
        return this.basePrice;
    }

    public Animal getProducerAnimal() {
        return this.producerAnimal;
    }
}
