package models;

import models.enums.environment.Time;
import models.enums.types.ShopType;

import java.util.ArrayList;
import java.util.HashMap;

public class Shop {
    private final String name;
    private final ShopType type;
    private final ArrayList<Item> shopInventory;
    private int balance = 0;
    private final NPC owner;
    private final int startHour;
    private final int endHour;

    public Shop(ShopType type) {
        this.type = type;
        this.shopInventory = new ArrayList<>(); // TODO
        this.name = type.getName();
        this.owner = type.getOwner();
        this.startHour = type.getStartHour();
        this.endHour = type.getEndHour();
    }

    public String getName() {
        return name;
    }

    public ShopType getType() {
        return type;
    }

    public ArrayList<Item> getShopInventory() {
        return shopInventory;
    }

    public int getBalance() {
        return balance;
    }

    public NPC getOwner() {
        return owner;
    }

    public boolean isOpen() {
        int currentHour = App.getCurrentGame().getGameState().getTime().getHour();
        if (currentHour >= startHour && currentHour <= endHour) {
            return true;
        }
        return false;
    }

    public void changeBalance(int amount) {
        this.balance += amount;
    }

    void addProduct(Item item, int count) {
        // TODO!
    }

    void removeProduct(Item item, int count) {
        // TODO
    }

    void sellProduct(Item item, int count) {
        // TODO
    }

    void showAvailableProducts() {
        // TODO
    }
}
