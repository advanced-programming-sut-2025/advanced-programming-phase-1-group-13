package com.ap_project.models;

public class Gift {
    private final int id;
    private final Item item;
    private final int amount;
    private final User giver;
    private int rating;

    public Gift(int id, Item item, int amount, User giver) {
        this.id = id;
        this.item = item;
        this.giver = giver;
        this.amount = amount;
        this.rating = 0;
    }

    public int getId() {
        return id;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public User getGiver() {
        return giver;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public int calculateFriendshipXP() {
        return (this.rating - 3) * 30 + 15;
    }
}
