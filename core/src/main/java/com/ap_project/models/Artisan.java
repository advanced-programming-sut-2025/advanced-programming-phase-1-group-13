package com.project.models;

import com.project.models.enums.types.ArtisanType;

public class Artisan {
    private final ArtisanType type;
    private Item itemPending;
    private int timeLeft; // in hours

    public Artisan(ArtisanType type) {
        this.type = type;
        this.itemPending = null;
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
}
