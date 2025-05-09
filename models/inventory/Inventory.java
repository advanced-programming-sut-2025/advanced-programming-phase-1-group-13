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
        return this.items;
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

    public String showItemsInInventory() {
        StringBuilder sb = new StringBuilder();
        for (Item item : items.keySet()) {
            sb.append(item.toString());
            sb.append("\n");
        }
        if (!isCapacityUnlimited) {
            int emptyLeft = capacity - items.size();
            sb.append("capacity: ").append(capacity).append("\n")
                    .append("empty spots left: ").append(emptyLeft);
        } else {
            sb.append("your inventory has unlimited capacity!");
        }
        return sb.toString();
    }
}
