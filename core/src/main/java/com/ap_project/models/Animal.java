package com.ap_project.models;

import com.ap_project.models.enums.Quality;
import com.ap_project.models.enums.environment.Time;
import com.ap_project.models.enums.types.AnimalType;

import java.util.ArrayList;

public class Animal {
    private final String name;
    private final AnimalType animalType;
    private int friendshipLevel = 0;
    private Time lastFeedingTime;
    private Time lastPettingTime;
    private Time lastProductTime;
    private ArrayList<AnimalProduct> producedProducts = new ArrayList<>();
    private final AnimalLivingSpace animalLivingSpace;
    private boolean isOutside = false;
    private Position position = null;

    public Animal(String name, AnimalType animalType, AnimalLivingSpace animalLivingSpace) {
        this.name = name;
        this.animalType = animalType;
        this.friendshipLevel = 0;
        Time now = App.getCurrentGame().getGameState().getTime();
        this.lastPettingTime = new Time(now);
        this.lastFeedingTime = new Time(now);
        this.lastProductTime = new Time(now);
        this.animalLivingSpace = animalLivingSpace;
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

    public boolean isOutside() {
        return isOutside;
    }

    public Position getPosition() {
        return position;
    }

    public void setLastFeedingTime(Time lastFeedingTime) {
        this.lastFeedingTime = new Time(lastFeedingTime);
    }

    public void setLastPettingTime(Time lastPettingTime) {
        this.lastPettingTime = lastPettingTime;
    }

    public void setLastProductTime(Time lastProductTime) {
        this.lastProductTime = lastProductTime;
    }

    public void setFriendshipLevel(int friendshipLevel) {
        this.friendshipLevel = friendshipLevel;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setOutside(boolean outside) {
        isOutside = outside;
    }

    public boolean hasBeenPetToday() {
        Time now = App.getCurrentGame().getGameState().getTime();
        return Time.areInSameDay(this.getLastPettingTime(), now);
    }

    public boolean hasBeenFedToday() {
        Time now = App.getCurrentGame().getGameState().getTime();
        return Time.areInSameDay(this.getLastFeedingTime(), now);
    }

    public void setProducedProducts(ArrayList<AnimalProduct> producedProducts) {
        this.producedProducts = producedProducts;
    }

    public void produceProduct() {
        int productIndex = 0;
        if (!(this.animalType.equals(AnimalType.DINOSAUR) || this.animalType.equals(AnimalType.SHEEP) ||
                this.animalType.equals(AnimalType.PIG))) {
            double secondTypeProbability = (this.friendshipLevel + (150 * (0.5 + Math.random())) / 1500);
            if (Math.random() > secondTypeProbability) {
                productIndex = 1;
            }
        }

        double qualityNumber = (this.friendshipLevel / 1000.0) * (0.5 + 0.5 * Math.random());
        Quality quality = Quality.getQualityByNumber(qualityNumber);


        AnimalProduct product = new AnimalProduct(this.animalType.getAnimalProducts().get(productIndex), quality, this);
        this.producedProducts.add(product);
    }

    public int calculatePrice() {
        return (int) (this.getAnimalType().getPrice() * ((double) this.getFriendshipLevel() / 1000 + 0.3));
    }
}
