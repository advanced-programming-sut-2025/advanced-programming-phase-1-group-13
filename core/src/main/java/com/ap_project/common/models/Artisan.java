package com.ap_project.common.models;

import com.ap_project.common.models.enums.types.ArtisanType;

public class Artisan {
    private final ArtisanType type;
    private Item itemPending;
    private int timeLeft; // in hours
    private final Position position;

    public Artisan(ArtisanType type) {
        this.type = type;
        this.itemPending = null;
        this.position = null;
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

    public Position getPosition() {
        return position;
    }

    public Result cancel() {
        if (itemPending == null) {
            return new Result(false, "No process to be cancelled.");
        }

        itemPending = null;
        timeLeft = -1;

        return new Result (true, "");
    }

    public Result cheat() {
        if (itemPending == null) {
            return new Result(false, "No item is being made.");
        }

        Result result = App.getLoggedIn().getBackpack().addToInventory(itemPending, 1);
        if (!result.success) {
            return new Result(false, "You don't have enough space in your backpack.");
        }
        itemPending = null;
        timeLeft = -1;
        return new Result(true, "");
    }
}
