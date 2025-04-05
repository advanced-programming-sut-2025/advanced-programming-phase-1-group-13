package models.animals;

import models.AnimalProduct;
import models.enums.environment.Time;

import java.util.ArrayList;

public abstract class Animal {
    private String name;
    private int purchaseCost;
    private int friendshipLevel;
    private Time LastFeedingTime;
    private Time lastProductTime;
    private ArrayList<AnimalProduct> animalProducts;
    private LivingSpace livingSpace;

    public void shepherdAnimal() {

    }

    public void feedHay() {

    }

    public void collectProduce() {

    }

    private void updateFriendship() {

    }

    public String getName() {
        return name;
    }

    public LivingSpace getLivingSpace() {
        return livingSpace;
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
