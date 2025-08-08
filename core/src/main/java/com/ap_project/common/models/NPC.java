package com.ap_project.common.models;

import com.ap_project.common.models.enums.environment.Season;
import com.ap_project.common.models.enums.environment.Weather;
import com.ap_project.common.models.enums.types.Dialog;
import com.ap_project.common.models.enums.types.ItemType;
import com.ap_project.common.models.enums.types.NPCType;
import com.ap_project.common.models.enums.types.Role;

import java.util.ArrayList;
import java.util.HashMap;

import static com.ap_project.common.models.Item.getItemTypeByItemName;

public class NPC {
    private final NPCType type;
    private final String name;
    private final Role role;
    private final ArrayList<ItemType> favorites;
    private ArrayList<Dialog> dialogs;
    private final Position position;
    private final HashMap<User, Boolean> giftReceivedToday;
    private final HashMap<User, Boolean> talkedToToday;
    private HashMap<User, Integer> daysLeftToUnlockThirdQuest;

    public NPC(NPCType type) {
        this.type = type;
        this.name = type.getName();
        this.role = type.getRole();
        this.position = type.getPosition();
        this.favorites = type.getFavorites();
        this.giftReceivedToday = new HashMap<>();
        this.talkedToToday = new HashMap<>();
        this.daysLeftToUnlockThirdQuest = new HashMap<>();
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

    public Dialog getDialog() {
        Game game = App.getCurrentGame();
        int timeOfDay = game.getGameState().getTime().getHour();
        Season season = game.getGameState().getTime().getSeason();
        Weather weather = game.getGameState().getCurrentWeather();
        int friendshipLevel = game.getNpcFriendshipPoints(App.getLoggedIn(), this);
        Dialog dialog = Dialog.getDialogBySituation(type, timeOfDay, season, weather, friendshipLevel);
        return dialog;
    }

    public boolean hasDialog() {
        return this.getDialog() != null;
    }
}
