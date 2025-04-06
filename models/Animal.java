package models;

import models.enums.environment.Time;
import models.enums.types.AnimalType;

import java.util.ArrayList;

public class Animal {
    private String name;
    private AnimalType animalType;
    private int purchaseCost;
    private int friendshipLevel;
    private Time LastFeedingTime;
    private Time lastProductTime;
    private ArrayList<AnimalProduct> producedProducts;
    private AnimalLivingSpace animalLivingSpace;

    public Animal(String name, AnimalType animalType) {
        this.name = name;
        this.animalType = animalType;
        this.friendshipLevel = 0;
    }

    public void shepherdAnimal() {
        // TODO
    }

    public void feedHay() {
        // TODO
    }

    public void collectProduce() {
        // TODO
    }

    private void updateFriendship() {
        // TODO
    }

    public String getName() {
        return this.name;
    }

    public AnimalLivingSpace getLivingSpace() {
        return this.animalLivingSpace;
    }

    public ArrayList<AnimalProduct> getProducedProducts() {
        return this.producedProducts;
    }

    public Time getLastProductTime() {
        return this.lastProductTime;
    }

    public Time getLastFeedingTime() {
        return this.LastFeedingTime;
    }

    public int getFriendshipLevel() {
        return this.friendshipLevel;
    }

    public AnimalType getAnimalType() {
        return this.animalType;
    }

    public AnimalLivingSpace getAnimalLivingSpace() {
        return this.animalLivingSpace;
    }

    public int getPurchaseCost() {
        return this.purchaseCost;
    }
}
