package models;

import models.enums.types.ShopType;

import java.util.HashMap;

public class Shop {
    private String name;
    private ShopType type;
    private HashMap<Item, Integer> shopInventory;
    private int balance;
    private NPC owner;

    public Shop(ShopType type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public HashMap<Item, Integer> getShopInventory() {
        return shopInventory;
    }

    public int getBalance() {
        return balance;
    }

    public NPC getOwner() {
        return owner;
    }

    void addProduct(Item item, int count) {
        // TODO
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
