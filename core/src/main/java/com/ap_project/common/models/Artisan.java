package com.ap_project.common.models;

import com.ap_project.common.models.enums.types.ArtisanType;
import com.ap_project.common.models.enums.types.ItemType;
import com.ap_project.common.models.enums.types.ProcessedItemType;

import java.util.ArrayList;
import java.util.Arrays;

public class Artisan {
    private final ArtisanType type;
    private Item itemPending;
    private int timeLeft;
    private int totalTime;
    private final Position position;

    public Artisan(ArtisanType type) {
        this.type = type;
        this.itemPending = null;
        this.position = null;
        this.timeLeft = -1;
    }

    public Artisan(ArtisanType type, Position position) {
        this.type = type;
        this.itemPending = null;
        this.position = position;
    }

    public ArtisanType getType() {
        return type;
    }

    public Item getItemPending() {
        return itemPending;
    }

    public void setItemPending(Item itemPending) {
        this.itemPending = itemPending;
    }

    public int getTimeLeft() {
        return timeLeft;
    }

    public void setTimeLeft(int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public int getTotalTime() {
        return totalTime;
    }

    public Position getPosition() {
        return position;
    }

    public Result startProcessing(String itemNamesString) {
        ArrayList<String> itemNames = new ArrayList<>(Arrays.asList(itemNamesString.split("\\s+")));
        ArrayList<ItemType> itemTypes = new ArrayList<>();
        for (String itemName : itemNames) {
            ItemType itemType = Item.getItemTypeByItemName(itemName);
            if (itemType == null) {
                return new Result(false, itemName + " is not a valid item.");
            }

            itemTypes.add(itemType);
        }

        User player = App.getLoggedIn();

        ProcessedItemType processedItemType = ProcessedItemType.getProcessedItemTypeByIngredients(itemTypes, type);
        if (processedItemType == null) {
            return new Result(false, type.getName() + " does not produce any items with these ingredients.");
        }

        for (ItemType itemType : itemTypes) {
            int quantity = processedItemType.getIngredients().get(itemType);
            Item item = Item.getItemByItemType(itemType);
            Result result = player.getBackpack().removeFromInventory(item, quantity);
            if (!result.success) {
                for (int i = 0; i < itemTypes.indexOf(itemType); i++) {
                    Item itemToRemove = Item.getItemByItemType(itemTypes.get(i));
                    player.getBackpack().addToInventory(itemToRemove,
                        processedItemType.getIngredients().get(itemTypes.get(i)));
                }
                return new Result(false, "You don't have enough ingredients.");
            }
        }

        int processingTime = processedItemType.getProcessingTime();
        itemPending = Item.getItemByItemType(processedItemType);
        timeLeft = processingTime;
        totalTime = processingTime;

        return new Result(true, "Started producing " + itemPending.getName());
    }


    public Result cancel() {
        if (itemPending == null) {
            return new Result(false, "No process to be cancelled.");
        }

        itemPending = null;
        timeLeft = -1;
        totalTime = -1;

        return new Result(true, "");
    }

    public Result collectItem() {
        if (itemPending == null) {
            return new Result(false, "No item is being made.");
        }

        Result result = App.getLoggedIn().getBackpack().addToInventory(itemPending, 1);
        if (!result.success) {
            return new Result(false, "You don't have enough space in your backpack.");
        }
        itemPending = null;
        timeLeft = -1;
        totalTime = -1;
        return new Result(true, "");
    }
}
