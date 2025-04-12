package models.enums.types;

import models.Item;

import java.util.ArrayList;
import java.util.HashMap;

public enum NPCType {
    //  TODO
//    CLINT(
//            Role.BLACKSMITH,
//            new HashMap<HashMap<Item, Integer>, HashMap<Item, Integer>>() {{
//                put(new HashMap<Item, Integer>() {{
//                        put(Item.IRON_ORE, 50);
//                    }},
//                        new HashMap<Item, Integer>() {{
//                            put(Item.DIAMOND, 2);
//                        }}
//                );
//            }},
//            new ArrayList<Item>() {{
//                add(Item.IRON_BAR);
//                add(Item.GOLD_BAR);
//            }}
//    ),
//    MORRIS(Role.SHOPKEEPER),
//    PIERRE(Role.SHOPKEEPER),
//    ROBIN(Role.SHOPKEEPER),
//    WILLY(Role.SHOPKEEPER),
//    MARNIE(Role.SHOPKEEPER),
//    GUS(Role.SHOPKEEPER),
//    SEBASTIAN(Role.VILLAGER),
//    ABIGAIL(Role.VILLAGER),
//    HARVEY(Role.VILLAGER),
//    LEA(Role.VILLAGER),
    ;
    private final Role role;
    private final HashMap<HashMap<Item, Integer>, // requests
            HashMap<Item, Integer> // rewards
            > quests;
    private final ArrayList<Item> favorites;

    NPCType(Role role, HashMap quests, ArrayList favorites) {
        this.role = role;
        this.quests = quests;
        this.favorites = favorites;
    }
}
