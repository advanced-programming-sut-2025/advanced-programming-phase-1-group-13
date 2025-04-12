package models.inventory;

import models.Item;

public abstract class Inventory {
    protected int capacity;
    protected boolean isCapacityUnlimited;

    public Inventory(int capacity, boolean isCapacityUnlimited) {
        this.capacity = capacity;
        this.isCapacityUnlimited = isCapacityUnlimited;
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
