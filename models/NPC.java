package models;

import models.enums.types.Dialog;
import models.enums.types.ItemType;
import models.enums.types.NPCType;
import models.enums.types.Role;

import java.util.ArrayList;
import java.util.HashMap;

import static models.Item.getItemTypeByItemName;

public class NPC {
    private NPCType type;
    private final String name;
    private Role role;
    private ArrayList<String> dialog;
    private HashMap<HashMap<ItemType, Integer>, // requests
            HashMap<ItemType, Integer> // rewards
            > quests;
    private ArrayList<ItemType> favorites;
    private ArrayList<Dialog> dialogs;
    private Position position;
    private HashMap<User, Boolean> giftReceivedToday; // TODO: reset every day
    private HashMap<User, Boolean> talkedToToday; // TODO: reset every day

    public NPC(NPCType type) {
        this.type = type;
        this.name = type.getName();
        this.role = type.getRole();
        this.dialog = new ArrayList<>();
        this.quests = type.getQuests();
        this.favorites = type.getFavorites();
        this.giftReceivedToday = new HashMap<>();
        this.talkedToToday = new HashMap<>();
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
}
