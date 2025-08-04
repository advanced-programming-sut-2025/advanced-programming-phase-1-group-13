package com.ap_project.common.models;

import com.ap_project.common.models.enums.types.Dialog;
import com.ap_project.common.models.enums.types.ItemType;
import com.ap_project.common.models.enums.types.NPCType;
import com.ap_project.common.models.enums.types.Role;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

import static com.ap_project.common.models.Item.getItemTypeByItemName;

public class NPC {
    private final NPCType type;
    private final String name;
    private final Role role;
    private final ArrayList<String> dialog;
    private final HashMap<HashMap<ItemType, Integer>, // requests
            HashMap<ItemType, Integer> // rewards
            > quests;
    private final ArrayList<ItemType> favorites;
    private ArrayList<Dialog> dialogs;
    private final Position position;
    private final HashMap<User, Boolean> giftReceivedToday;
    private final HashMap<User, Boolean> talkedToToday;
    private HashMap<User, Integer> daysLeftToUnlockThirdQuest;
    private HashMap<User, Boolean> thirdQuestUnlocked;
    private final ArrayList<Boolean> questsFinished;

    public NPC(NPCType type) {
        this.type = type;
        this.name = type.getName();
        this.role = type.getRole();
        this.position = new Position((new Random()).nextInt(30), (new Random()).nextInt(30));
        this.dialog = new ArrayList<>();
        this.quests = type.getQuests();
        this.favorites = type.getFavorites();
        this.giftReceivedToday = new HashMap<>();
        this.talkedToToday = new HashMap<>();
        this.daysLeftToUnlockThirdQuest = new HashMap<>();
        this.thirdQuestUnlocked = new HashMap<>();
        this.questsFinished = new ArrayList<>();
    }

    public void setQuestFinished(Boolean value, int index) {
        this.questsFinished.set(index, value);
    }

    public NPCType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public ArrayList<String> getDialog() {
        return dialog;
    }

    public HashMap<HashMap<ItemType, Integer>, // requests
            HashMap<ItemType, Integer> // rewards
            > getQuests() {
        return quests;
    }

    public ArrayList<ItemType> getFavorites() {
        return favorites;
    }

    public ArrayList<Dialog> getDialogs() {
        return dialogs;
    }

    public Position getPosition() {
        return position;
    }

    public HashMap<User, Boolean> getGiftReceivedToday() {
        return giftReceivedToday;
    }

    public boolean hasReceivedGiftToday(User user) {
        return this.getGiftReceivedToday().get(user);
    }

    public void setReceivedGiftToday(User user, boolean hasReceived) {
        this.getGiftReceivedToday().put(user, hasReceived);
    }

    public HashMap<User, Boolean> getTalkedToToday() {
        return talkedToToday;
    }

    public boolean hasTalkedToToday(User user) {
        return this.getTalkedToToday().get(user);
    }

    public void setTalkedToToday(User user, boolean hasReceived) {
        this.getTalkedToToday().put(user, hasReceived);
    }

    public boolean isFavourite(String itemName) {
        return this.getType().getFavorites().contains(getItemTypeByItemName(itemName));
    }

    public HashMap<User, Integer> getDaysLeftToUnlockThirdQuest() {
        return daysLeftToUnlockThirdQuest;
    }

    public void setDaysLeftToUnlockThirdQuest(HashMap<User, Integer> daysLeftToUnlockThirdQuest) {
        this.daysLeftToUnlockThirdQuest = daysLeftToUnlockThirdQuest;
    }

    public void startThirdQuestCountdown(User user) {
        this.daysLeftToUnlockThirdQuest.put(user, this.type.getDaysToUnlockThirdQuest());
    }

    public void changeThirdQuestTime(User user, int amount) {
        int currentAmount = this.daysLeftToUnlockThirdQuest.get(user);
        this.daysLeftToUnlockThirdQuest.put(user, currentAmount + amount);
    }

    public HashMap<User, Boolean> getThirdQuestUnlocked() {
        return thirdQuestUnlocked;
    }

    public void setThirdQuestUnlocked(HashMap<User, Boolean> thirdQuestUnlocked) {
        this.thirdQuestUnlocked = thirdQuestUnlocked;
    }

    public void unlockThirdQuest(User user) {
        this.thirdQuestUnlocked.put(user, true);
    }

    public boolean isQuestFinished(int index) {
        return this.questsFinished.get(index);
    }
}
