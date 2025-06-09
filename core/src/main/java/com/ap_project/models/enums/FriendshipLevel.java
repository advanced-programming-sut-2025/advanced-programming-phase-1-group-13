package com.project.models.enums;

public enum FriendshipLevel {
    STRANGER(0),
    FRIEND(1),
    CLOSE_FRIEND(2),
    BEST_FRIEND(3),
    MARRIED(4);

    private final int number;

    FriendshipLevel(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int requiredXPForNextLevel() {
        return (this.number + 1) * 100;
    }

    public static FriendshipLevel getFriendshipLevelByNumber(int number) {
        for (FriendshipLevel friendshipLevel : FriendshipLevel.values()) {
            if (friendshipLevel.getNumber() == number) {
                return friendshipLevel;
            }
        }
        return null;
    }
}
