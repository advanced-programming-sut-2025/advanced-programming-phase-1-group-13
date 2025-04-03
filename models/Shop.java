package models;

import java.util.HashMap;

public class Shop {
    String name;
    HashMap<Item, Integer> inventory;
    int balance;
    NPC owner;

    public Shop(String name, HashMap<Item, Integer> inventory, int balance, NPC owner) {
        this.name = name;
        this.inventory = inventory;
        this.balance = balance;
        this.owner = owner;
    }

    public String getName() {
        return name;
    }

    public HashMap<Item, Integer> getInventory() {
        return inventory;
    }

    public int getBalance() {
        return balance;
    }

    public NPC getOwner() {
        return owner;
    }

    void addProduct(Item item, int count) {

    }

    void removeProduct(Item item, int count) {

    }

    void sellProduct(Item item, int count) {

    }

    void showAvailableProducts() {

    }
}
