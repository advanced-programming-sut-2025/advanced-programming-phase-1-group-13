package models;

import models.enums.FriendshipLevel;

public class Friendship {
    private FriendshipLevel level;
    private int currentXP;

    public Friendship() {
        this.level = FriendshipLevel.STRANGER;
        this.currentXP = 0;
    }

    public FriendshipLevel getLevel() {
        return level;
    }

    public int getCurrentXP() {
        return currentXP;
    }

    public void increaseXP(int amount, User user) {
        // TODO
    }

    public void decreaseXP(int amount, User user) {
        // TODO
    }

    public int requiredXPForNextLevel() {
        // TODO
        return 0;
    }

    public boolean isMaxLevel() {
        // TODO
        return false;
    }
}
