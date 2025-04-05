package models.animals;

import models.Product;
import models.enums.environment.Time;

import java.util.ArrayList;

public abstract class Animal {
    private String name;
    private int purchaseCost;
    private int friendshipLevel;
    private Time LastFeedingTime;
    private Time lastProductTime;
    private ArrayList<Product> products;
    private LivingSpace livingSpace;

    public void shepherdAnimal() {

    }

    public void feedHay() {

    }

    public void collectProduce() {

    }

    private void updateFriendship() {

    }
}
