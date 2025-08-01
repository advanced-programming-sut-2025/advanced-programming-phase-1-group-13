package com.ap_project.common.models.inventory;

import com.ap_project.common.models.AnimalProduct;
import com.ap_project.common.models.Item;
import com.ap_project.common.models.Result;
import com.ap_project.common.models.User;
import com.ap_project.common.models.enums.types.ToolMaterial;
import com.ap_project.common.models.tools.TrashCan;

import java.util.HashMap;

public abstract class Inventory {
    protected int capacity;
    protected boolean isCapacityUnlimited;
    protected HashMap<Item, Integer> items;

    public Inventory(int capacity, boolean isCapacityUnlimited) {
        this.items = new HashMap<>();
        this.capacity = capacity;
        this.isCapacityUnlimited = isCapacityUnlimited;
    }

    public HashMap<Item, Integer> getItems() {
        return this.items;
    }

    public Result addToInventory(Item item, Integer n) {
        if (n == null) n = 1;
        if (isCapacityUnlimited || this.getItems().size() < this.capacity) {
            if (this.getItems().containsKey(item)) {
                items.put(item, this.getItems().get(item) + n);
            } else {
                items.put(item, n);
            }
            return new Result(true, "Successfully added " + n + " of " + item.getName() + " to the inventory.");
        }
        return new Result(false, "Capacity limit exceeded.");
    }

    public void CheatAddToInventory(Item item, int n) {
        this.addToInventory(item, n);
    }

    public int getCapacity() {
        return this.capacity;
    }

    public boolean isCapacityUnlimited() {
        return this.isCapacityUnlimited;
    }

    public Result removeFromInventoryToTrash(Item item, Integer n, User player) {
        boolean inventoryHasItem = false;
        Item theItem = null;

        for (Item itemInSet : items.keySet()) {
            if (itemInSet.getName().equals(item.getName())) {
                inventoryHasItem = true;
                theItem = itemInSet;
                break;
            }
        }

        if (!inventoryHasItem) {
            return new Result(false, "Item does not exist.");
        }

        int currentQuantity = items.get(theItem);
        if (n != null && currentQuantity < n) {
            String message = "The provided number is larger than the quantity in inventory.\n" +
                    "You have " + currentQuantity + " " + item + " in your inventory.";
            return new Result(false, message);
        }
        ToolMaterial trashCanMaterial = player.getTrashCan().getToolMaterial();
        if (n == null) {
            return removeAllOfThatItemToTrash(item, player, trashCanMaterial);
        }
        int newQuantity = currentQuantity - n;
        if (newQuantity == 0) {
            return removeAllOfThatItemToTrash(item, player, trashCanMaterial);
        } else {
            items.put(item, newQuantity);
            double moneyToEarn = TrashCan.calculateMoneyToEarnViaTrashCan(trashCanMaterial, n, item.getPrice());
            player.changeBalance(moneyToEarn);
            String message = n + " of item <" + item + "> has been removed from inventory.\n" +
                    "You now have " + newQuantity + " of that item in your inventory.\n" +
                    "You earned " + moneyToEarn + ".";
            return new Result(true, message);
        }
    }

    private Result removeAllOfThatItemToTrash(Item item, User player, ToolMaterial trashCanMaterial) {
        items.remove(item);
        Integer numOfItem = items.get(item);
        Integer itemPrice = item.getPrice();
        double moneyToEarn = TrashCan.calculateMoneyToEarnViaTrashCan(trashCanMaterial, numOfItem, itemPrice);
        player.changeBalance(moneyToEarn);

        String message = "Item removed from inventory.\n" +
                "You earned " + moneyToEarn + ".";
        return new Result(true, message);
    }

    public String showItemsInInventory() {
        if (items.isEmpty()) {
            return "Your inventory is empty.";
        }
        StringBuilder sb = new StringBuilder();
        for (Item item : items.keySet()) {
            sb.append(item.getName()).append(" : ").append(items.get(item)).append("\n");
        }
        sb.append("\n");
        if (!isCapacityUnlimited) {
            int emptyLeft = capacity - items.size();
            sb.append("capacity: ").append(capacity).append("\n")
                    .append("empty spots left: ").append(emptyLeft);
        } else {
            sb.append("Your inventory has unlimited capacity!");
        }
        return sb.toString();
    }

    public Result removeFromInventory(Item item, Integer n) {
        boolean found = false;
        Item itemToRemove = null;
        for (Item item2 : items.keySet()) {
            if (item.getName().equals(item2.getName())) {
                found = true;
                itemToRemove = item2;
            }
        }
        if (!found) {
            return new Result(false, "Item does not exist." + item.getName());
        }
        int currentQuantity = items.get(itemToRemove);
        if (n != null && currentQuantity < n) {
            String message = "The provided number is larger than the quantity in inventory.\n" +
                    "You have " + currentQuantity + " " + item + " in your inventory.";
            return new Result(false, message);
        }
        if (n == null) {
            items.remove(item);
            String message = "Item removed from inventory.";
            return new Result(true, message);
        }
        int newQuantity = currentQuantity - n;
        if (newQuantity == 0) {
            items.remove(item);
            String message = "Item removed from inventory.";
            return new Result(true, message);
        } else {
            items.put(item, newQuantity);
            String message = n + " of item <" + item + "> has been removed from inventory.\n" +
                    "You now have " + newQuantity + " of that item in your inventory.\n";
            return new Result(true, message);
        }
    }

    public Item getItemFromInventoryByName(String itemName) {
        for (Item item : items.keySet()) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }
}
