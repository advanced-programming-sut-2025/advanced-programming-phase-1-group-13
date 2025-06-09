package com.ap_project.models;

import com.ap_project.models.enums.FriendshipLevel;

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

    public void setLevel(FriendshipLevel level) {
        this.level = level;
        this.currentXP = 0;
    }

    public void updateFriendship(int amount) {
        this.currentXP += amount;
        if (this.currentXP < 0) {
            int levelNumber = this.getLevel().getNumber() - 1;
            this.level = FriendshipLevel.getFriendshipLevelByNumber(levelNumber);
            if (this.level == null) {
                this.level = FriendshipLevel.STRANGER;
            } else {
                this.currentXP += this.level.requiredXPForNextLevel();
            }
        }
        if (this.currentXP > this.level.requiredXPForNextLevel()) {
            int levelNumber = this.getLevel().getNumber() + 1;
            if (levelNumber < 3) {
                this.currentXP += this.level.requiredXPForNextLevel();
                this.level = FriendshipLevel.getFriendshipLevelByNumber(levelNumber);
            } else {
                this.currentXP = FriendshipLevel.CLOSE_FRIEND.requiredXPForNextLevel();
            }
        }
    }

    @Override
    public String toString() {
        return this.level.getNumber() + " (" + this.currentXP + " XP).";
    }
}
