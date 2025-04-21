package models.inventory;

import models.Item;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class Inventory {
    protected int capacity;
    protected boolean isCapacityUnlimited;
    protected HashMap<Item, Integer> items;

    public Inventory(int capacity, boolean isCapacityUnlimited) {
        this.capacity = capacity;
        this.isCapacityUnlimited = isCapacityUnlimited;
    }

    public HashMap<Item, Integer> getItems() {
        return items;
    }

    public void addToInventory(Item item, int n) {
        // TODO
    }

    public void CheatAddToInventory(Item item, int n) {
        // TODO
    }

    public void removeFromInventory(Item item, int n) {
        // TODO
    }
}
