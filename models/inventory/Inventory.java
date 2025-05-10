package models.inventory;

import models.Item;
import models.Result;
import models.User;
import models.enums.types.ToolMaterial;
import models.tools.TrashCan;

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

    public int getCapacity() {
        return this.capacity;
    }

    public boolean isCapacityUnlimited() {
        return this.isCapacityUnlimited;
    }

    public Result removeFromInventory(Item item, Integer n, User player) {
        if (!items.containsKey(item)) {
            return new Result(false, "Item does not exist.");
        }
        int currentQuantity = items.get(item);
        if (n != null && currentQuantity < n) {
            String message = "The provided number is larger than the quantity in inventory.\n" +
                    "You have " + currentQuantity + " " + item + " in your inventory.";
            return new Result(false, message);
        }
        ToolMaterial trashCanMaterial = player.getTrashCan().getToolMaterial();
        if (n == null) {
            return removeAllOfThatItem(item, player, trashCanMaterial);
        }
        int newQuantity = currentQuantity - n;
        if (newQuantity == 0) {
            return removeAllOfThatItem(item, player, trashCanMaterial);
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

    private Result removeAllOfThatItem(Item item, User player, ToolMaterial trashCanMaterial) {
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

    public Item getItemFromInventory(String itemName) {
        for (Item item : items.keySet()) {
            if (item.getName().equals(itemName)) {
                return item;
            }
        }
        return null;
    }
}
