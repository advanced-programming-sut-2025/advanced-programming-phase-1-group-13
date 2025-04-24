package models;

import models.enums.environment.Time;
import models.enums.types.AnimalType;

import java.util.ArrayList;

public class Animal {
    private String name;
    private AnimalType animalType;
    private int friendshipLevel = 0;
    private Time lastFeedingTime;
    private Time lastPettingTime;
    private Time lastProductTime;
    private ArrayList<AnimalProduct> producedProducts = new ArrayList<>();
    private AnimalLivingSpace animalLivingSpace;

    public Animal(String name, AnimalType animalType, AnimalLivingSpace animalLivingSpace) {
        this.name = name;
        this.animalType = animalType;
        this.friendshipLevel = 0;
        Time now = App.getCurrentGame().getGameState().getTime();
        this.lastPettingTime = now;
        this.lastFeedingTime = now;
        this.lastProductTime = now;
        this.animalLivingSpace = animalLivingSpace;
    }

    public void feedHay() {
        // TODO
    }

    public void collectProduce() {
        // TODO
    }

    public void changeFriendship(int amount) {
        this.friendshipLevel += amount;
        if (this.friendshipLevel > 1000) {
            this.friendshipLevel = 1000;
        }
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

    public Time getLastPettingTime() {
        return this.lastPettingTime;
    }

    public Time getLastFeedingTime() {
        return this.lastFeedingTime;
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

    public void setLastFeedingTime(Time lastFeedingTime) {
        this.lastFeedingTime = lastFeedingTime;
    }

    public void setLastPettingTime(Time lastPettingTime) {
        this.lastPettingTime = lastPettingTime;
    }

    public void setLastProductTime(Time lastProductTime) {
        this.lastProductTime = lastProductTime;
    }
}
