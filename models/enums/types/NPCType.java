package models.enums.types;

import models.Item;

import java.util.ArrayList;
import java.util.HashMap;

public enum NPCType {
    CLINT(Role.BLACKSMITH),
    MORRIS(Role.SHOPKEEPER),
    PIERRE(Role.SHOPKEEPER),
    ROBIN(Role.SHOPKEEPER),
    WILLY(Role.SHOPKEEPER),
    MARNIE(Role.SHOPKEEPER),
    GUS(Role.SHOPKEEPER),
    SEBASTIAN(Role.VILLAGER),
    ABIGAIL(Role.VILLAGER),
    HARVEY(Role.VILLAGER),
    LEA(Role.VILLAGER);

    Role role;
    HashMap<HashMap<Item, Integer>, // requests
            HashMap<Item, Integer> // rewards
            > quests;
    ArrayList<Item> Favorites;

    NPCType(Role role) {
        this.role = role;
    }
}
