package models.enums.types;

import models.farming.Crop;
import models.tools.Tool;

import java.util.ArrayList;
import java.util.HashMap;

public enum NPCType {
    CLINT(
            Role.SHOPKEEPER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>(),
            new ArrayList<ItemType>()
    ),
    MORRIS(Role.SHOPKEEPER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>(),
            new ArrayList<ItemType>()
    ),
    PIERRE(Role.SHOPKEEPER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>(),
            new ArrayList<ItemType>()
    ),
    ROBIN(Role.SHOPKEEPER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>() {{
                put(
                        new HashMap<ItemType, Integer>() {{
                            put(MaterialType.WOOD, 80);
                            put(MineralType.IRON_BAR, 10);
                            put(MaterialType.WOOD, 1000);
                        }},
                        new HashMap<ItemType, Integer>() {{
                            put(MineralType.GOLD_COIN, 1000);
                            put(GoodsType.BEEHIVE, 3);
                            put(MineralType.COIN, 25000);
                        }}
                );
            }},
            new ArrayList<ItemType>() {{
                add(FoodType.SPAGHETTI);
                add(MaterialType.WOOD);
                add(MineralType.IRON_BAR);
            }}
    ),
    WILLY(Role.SHOPKEEPER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>(),
            new ArrayList<ItemType>()
    ),
    MARNIE(Role.SHOPKEEPER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>(),
            new ArrayList<ItemType>()
    ),
    GUS(Role.SHOPKEEPER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>(),
            new ArrayList<ItemType>()
    ),
    SEBASTIAN(Role.VILLAGER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>() {{
                put(
                        new HashMap<ItemType, Integer>() {{
                            put(MineralType.IRON, 50);
                            put(FoodType.PUMPKIN_PIE, 1);
                            put(MineralType.STONE, 150);
                        }},
                        new HashMap<ItemType, Integer>() {{
                            put(MineralType.DIAMOND, 2);
                            put(MineralType.GOLD_COIN, 5000);
                            put(MineralType.QUARTZ, 50);
                        }}
                );
            }},
            new ArrayList<ItemType>() {{
                add(AnimalProductType.WOOL);
                add(FoodType.PUMPKIN_PIE);
                add(FoodType.PIZZA);
            }}
    ),
    ABIGAIL(Role.VILLAGER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>() {{
                put(
                        new HashMap<ItemType, Integer>() {{
                            put(MineralType.GOLD_BAR, 1);
                            put(CropType.PUMPKIN, 1);
                            put(CropType.WHEAT, 50);
                        }},
                        new HashMap<ItemType, Integer>() {{
                            put(null, 0); // Friendship XP
                            put(MineralType.GOLD_COIN, 500);
                            put(ToolTypes.WATERING_CAN, 1);
                        }}
                );
            }},
            new ArrayList<ItemType>() {{
                add(MineralType.STONE);
                add(MineralType.IRON_ORE);
                add(GoodsType.COFFEE);
            }}
    ),
    HARVEY(Role.VILLAGER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>() {{
                put(
                        new HashMap<ItemType, Integer>() {{
                            put(null, 1); // Friendship XP
                            put(FishType.SALMON, 1);
                            put(ProcessedItemType.WINE, 1);
                        }},
                        new HashMap<ItemType, Integer>() {{
                            put(MineralType.GOLD_COIN, 750);
                            put(null, 1); // Friendship XP
                            put(FoodType.SALAD, 5);
                        }}
                );
            }},
            new ArrayList<ItemType>() {{
                add(GoodsType.COFFEE);
                add(FoodType.VEGETABLE_MEDLEY);
                add(ProcessedItemType.WINE);
            }}
    ),
    LEA(Role.VILLAGER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>() {{
                put(
                        new HashMap<ItemType, Integer>() {{
                            put(MaterialType.HARD_WOOD, 10);
                            put(FishType.SALMON, 1);
                            put(MaterialType.WOOD, 200);
                        }},
                        new HashMap<ItemType, Integer>() {{
                            put(MineralType.GOLD_COIN, 500);
                            put(FoodType.SALMON_DINNER, 1);
                            put(GoodsType.SCARE_CROW, 3);
                        }}
                );
            }},
            new ArrayList<ItemType>() {{
                add(FoodType.SALAD);
                add(CropType.GRAPE);
                add(ProcessedItemType.WINE);
            }}
    )
    ;

    private final Role role;
    private final HashMap<HashMap<ItemType, Integer>, // requests
            HashMap<ItemType, Integer> // rewards
            > quests;
    private final ArrayList<ItemType> favorites;

    NPCType(Role role, HashMap quests, ArrayList favorites) {
        this.role = role;
        this.quests = quests;
        this.favorites = favorites;
    }

    public Role getRole() {
        return role;
    }

    public HashMap<HashMap<ItemType, Integer>, // requests
            HashMap<ItemType, Integer> // rewards
            > getQuests() {
        return quests;
    }

    public ArrayList<ItemType> getFavorites() {
        return favorites;
    }
}
