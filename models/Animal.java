package models;

import models.enums.environment.Time;
import models.enums.types.AnimalType;

import java.util.ArrayList;

public abstract class Animal {
    private String name;
    private AnimalType animalType;
    private int purchaseCost;
    private int friendshipLevel;
    private Time LastFeedingTime;
    private Time lastProductTime;
    private ArrayList<AnimalProduct> animalProducts;
    private AnimalLivingSpace animalLivingSpace;

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
        return name;
    }

    public AnimalLivingSpace getLivingSpace() {
        return animalLivingSpace;
    }

    public ArrayList<AnimalProduct> getAnimalProducts() {
        return animalProducts;
    }

    public Time getLastProductTime() {
        return lastProductTime;
    }

    public Time getLastFeedingTime() {
        return LastFeedingTime;
    }

    public int getFriendshipLevel() {
        return friendshipLevel;
    }

    public int getPurchaseCost() {
        return purchaseCost;
    }
}
