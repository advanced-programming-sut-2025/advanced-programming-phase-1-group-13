package models;

import models.enums.environment.Time;
import models.enums.types.ItemType;
import models.enums.types.NPCType;
import models.enums.types.Role;

import java.util.ArrayList;
import java.util.HashMap;

public class NPC {
    private NPCType type;
    private Role role;
    private ArrayList<String> dialog;
    private HashMap<HashMap<ItemType, Integer>, // requests
            HashMap<ItemType, Integer> // rewards
            > quests;
    private ArrayList<ItemType> favorites;

    public NPC(NPCType type) {
        this.type = type;
        this.role = type.getRole();
        this.dialog = new ArrayList<>();
        this.quests = type.getQuests();
        this.favorites = type.getFavorites();
    }

    public NPCType getType() {
        return type;
    }

    public Role getRole() {
        return role;
    }

    public ArrayList<String> getDialog() {
        return dialog;
    }

    public void addDialog(String sentence) {
        // TODO
    }
}
