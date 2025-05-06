package models;

import models.enums.types.Dialog;
import models.enums.types.ItemType;
import models.enums.types.NPCType;
import models.enums.types.Role;

import java.util.ArrayList;
import java.util.HashMap;

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

    public NPC(NPCType type) {
        this.type = type;
        this.name = type.getName();
        this.role = type.getRole();
        this.dialog = new ArrayList<>();
        this.quests = type.getQuests();
        this.favorites = type.getFavorites();
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

    public void addDialog(String sentence) {
        // TODO
    }
}
