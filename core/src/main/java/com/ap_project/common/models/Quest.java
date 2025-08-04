package com.ap_project.common.models;

import com.ap_project.common.models.enums.types.ItemType;

import java.util.ArrayList;

public class Quest {
    private final int id;
    private final NPC npc;
    private final ItemType request;
    private final int requestQuantity;
    private final ItemType reward;
    private final int rewardQuantity;
    private final ArrayList<User> unlockedUsers;
    private boolean isFinished;
    private User finisher;

    public Quest(int id, NPC npc, ItemType request, int requestQuantity, ItemType reward, int rewardQuantity, Game game) {
        this.id = id;
        this.npc = npc;
        this.request = request;
        this.requestQuantity = requestQuantity;
        this.reward = reward;
        this.rewardQuantity = rewardQuantity;
        this.unlockedUsers = new ArrayList<>();
        if (id <= 5) {
            unlockedUsers.addAll(game.getPlayers());
        }
        this.isFinished = false;
    }

    public int getId() {
        return id;
    }

    public NPC getNpc() {
        return npc;
    }

    public ItemType getRequest() {
        return request;
    }

    public int getRequestQuantity() {
        return requestQuantity;
    }

    public ItemType getReward() {
        return reward;
    }

    public int getRewardQuantity() {
        return rewardQuantity;
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
