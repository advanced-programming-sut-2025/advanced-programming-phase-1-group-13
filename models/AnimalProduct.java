package models;

import models.enums.Quality;
import models.enums.types.AnimalProductType;

public class AnimalProduct extends Item {
    private AnimalProductType type;
    private int basePrice;
    private Quality quality;
    private Animal producerAnimal;

    public AnimalProduct(AnimalProductType type, Quality quality, Animal producerAnimal) {
        this.type = type;
        this.quality = quality;
        this.producerAnimal = producerAnimal;
        this.basePrice = type.getBasePrice();
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

    public int calculatePrice() {
        return (int) (this.basePrice * this.quality.getPriceCoefficient());
    }
}
