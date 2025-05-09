package models.inventory;

import models.Item;
import models.Result;

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
        items.put(item, n);
    }

    public void CheatAddToInventory(Item item, int n) {
        // TODO
    }

    public Result removeFromInventory(Item item, Integer n) {
        if (!items.containsKey(item)) {
            return new Result(false, "Item does not exist.");
        }
        if (n == null) {
            items.remove(item);
            return new Result(true, "Item removed from inventory.");
        }
        int currentQuantity = items.get(item);
        if (currentQuantity < n) {
            String message = "The provided number is larger than the quantity in inventory." + "\n" +
                    "You have " + currentQuantity + " " + item + " in your inventory.";
            return new Result(false, message);
        }
        int newQuantity = currentQuantity - n;
        if (newQuantity == 0) {
            items.remove(item);
            return new Result(true, "Item removed from inventory.");
        } else {
            items.put(item, newQuantity);
            String message = n + " of item <" + item + "> has been removed from inventory." + "\n" +
                    "You now have " + newQuantity + " of that item in your inventory.";
            return new Result(true, message);
        }
    }

    public String showItemsInInventory() {
        if (items.isEmpty()) {
            return "Your inventory is empty.";
        }
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
            sb.append("Your inventory has unlimited capacity!");
        }
        return sb.toString();
    }
}
