package com.ap_project.common.models;

import com.ap_project.common.models.enums.types.ItemType;

import java.util.ArrayList;

public class GroupQuest {
    private final int id;
    private final ArrayList<User> unlockedUsers;
    private boolean isFinished;
    private User finisher;

    public GroupQuest(int id, Game game) {
        this.id = id;
        this.unlockedUsers = new ArrayList<>();
        if (id <= 5) {
            unlockedUsers.addAll(game.getPlayers());
        }
        this.isFinished = false;
    }

    public int getId() {
        return id;
    }

    public boolean isFinished() {
        return isFinished;
    }

    public User getFinisher() {
        return finisher;
    }

    public boolean isUnlocked(User user) {
        return unlockedUsers.contains(user);
    }

    public void finish(User user) {
        isFinished = true;
        finisher = user;
    }

    public void unlock(User user) { // TODO: unlock second and third quests
        unlockedUsers.add(user);
    }
}
