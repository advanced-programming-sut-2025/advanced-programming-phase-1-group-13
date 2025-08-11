package com.ap_project.common.models.enums.types;

import com.ap_project.common.models.Position;

import java.util.*;

public enum NPCType {
    CLINT(
        "Clint",
        Role.SHOPKEEPER,
        null,
        Collections.emptyList(),
        new ArrayList<>(),
        14,
        new ArrayList<ItemType>() {{
            add(GoodsType.IRON_ORE);
            add(GoodsType.COPPER_ORE);
            add(GoodsType.GOLD_ORE);
        }}
    ),
    MORRIS(
        "Morris",
        Role.SHOPKEEPER,
        null,
        Collections.emptyList(),
        new ArrayList<>(),
        10,
        new ArrayList<ItemType>() {{
            add(GoodsType.COFFEE);
            add(FoodType.VEGETABLE_MEDLEY);
            add(ProcessedItemType.WINE);
        }}
    ),
    PIERRE(
        "Pierre",
        Role.SHOPKEEPER,
        null,
        Collections.emptyList(),
        new ArrayList<>(),
        20,
        new ArrayList<ItemType>() {{
            add(GoodsType.STARFRUIT_SEEDS);
            add(GoodsType.JOJA_COLA);
            add(GoodsType.COFFEE);
        }}
    ),
    WILLY(
        "Willy",
        Role.SHOPKEEPER,
        null,
        Collections.emptyList(),
        new ArrayList<>(),
        25,
        new ArrayList<ItemType>() {{
            add(FishType.GLACIERFISH);
            add(FishType.SALMON);
            add(FishType.LEGEND);
        }}
    ),
    MARNIE(
        "Marnie",
        Role.SHOPKEEPER,
        null,
        Collections.emptyList(),
        new ArrayList<>(),
        15,
        new ArrayList<ItemType>() {{
            add(AnimalProductType.CHICKEN_EGG);
            add(AnimalProductType.WOOL);
            add(AnimalProductType.COW_MILK);
        }}
    ),
    GUS(
        "Gus",
        Role.SHOPKEEPER,
        null,
        Collections.emptyList(),
        new ArrayList<>(),
        20,
        new ArrayList<ItemType>() {{
            add(FoodType.DISH_O_THE_SEA);
            add(FoodType.PIZZA);
            add(ProcessedItemType.WINE);
        }}
    ),
    SEBASTIAN(
        "Sebastian",
        Role.VILLAGER,
        new Position(10, 10),
        Arrays.asList(
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MineralType.IRON_ORE, 50);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MineralType.DIAMOND, 2);
                }}
            ),
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(FoodType.PUMPKIN_PIE, 1);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MineralType.QUARTZ, 50);
                }}
            ),
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MineralType.STONE, 150);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MoneyType.GOLD_COIN, 5000);
                }}
            )
        ),
        new ArrayList<ItemType>() {{
            add(AnimalProductType.WOOL);
            add(FoodType.PUMPKIN_PIE);
            add(FoodType.PIZZA);
        }},
        42,
        new ArrayList<ItemType>() {{
            add(CropType.POPPY);
            add(FoodType.PUMPKIN_PIE);
            add(ProcessedItemType.BEER);
        }}
    ),
    ABIGAIL(
        "Abigail",
        Role.VILLAGER,
        new Position(50, 10),
        Arrays.asList(
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(IngredientType.GOLD_BAR, 1);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(null, 0); // Friendship XP
                }}
            ),
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(CropType.PUMPKIN, 1);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MoneyType.GOLD_COIN, 500);
                }}
            ),
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(CropType.WHEAT, 50);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(ToolType.WATERING_CAN, 1);
                }}
            )
        ),
        new ArrayList<ItemType>() {{
            add(MineralType.STONE);
            add(GoodsType.IRON_ORE);
            add(GoodsType.COFFEE);
        }},
        29,
        new ArrayList<ItemType>() {{
            add(MineralType.QUARTZ);
            add(FoodType.VEGETABLE_MEDLEY);
            add(ProcessedItemType.MAYONNAISE);
        }}
    ),
    HARVEY(
        "Harvey",
        Role.VILLAGER,
        new Position(30, 40),
        Arrays.asList(
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(null, 1); // Plant
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MoneyType.GOLD_COIN, 750);
                }}
            ),
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(FishType.SALMON, 1);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(null, 1); // Friendship XP
                }}
            ),
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(ProcessedItemType.WINE, 1);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(FoodType.SALAD, 5);
                }}
            )
        ),
        new ArrayList<ItemType>() {{
            add(GoodsType.COFFEE);
            add(FoodType.VEGETABLE_MEDLEY);
            add(ProcessedItemType.WINE);
        }},
        61,
        new ArrayList<ItemType>() {{
            add(FruitType.APPLE);
            add(ProcessedItemType.TRUFFLE_OIL);
            add(ProcessedItemType.COFFEE);
        }}
    ),
    LEAH(
        "Leah",
        Role.VILLAGER,
        new Position(50, 10),
        Arrays.asList(
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MaterialType.HARD_WOOD, 10);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MoneyType.GOLD_COIN, 500);
                }}
            ),
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(FishType.SALMON, 1);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(FoodType.SALMON_DINNER, 1);
                }}
            ),
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MaterialType.WOOD, 200);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(CraftType.SCARECROW, 3);
                }}
            )
        ),
        new ArrayList<ItemType>() {{
            add(FoodType.SALAD);
            add(CropType.GRAPE);
            add(ProcessedItemType.WINE);
        }},
        14,
        new ArrayList<ItemType>() {{
            add(ForagingCropType.BLACKBERRY);
            add(FoodType.SALAD);
            add(ProcessedItemType.MEAD);
        }}
    ),
    ROBIN(
        "Robin",
        Role.SHOPKEEPER,
        new Position(10, 50),
        Arrays.asList(
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MaterialType.WOOD, 80);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MoneyType.GOLD_COIN, 1000);
                }}
            ),
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(IngredientType.IRON_BAR, 10);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(CraftType.BEE_HOUSE, 3);
                }}
            ),
            new AbstractMap.SimpleEntry<>(
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MaterialType.HARD_WOOD, 1000);
                }},
                new LinkedHashMap<ItemType, Integer>() {{
                    put(MoneyType.COIN, 25000);
                }}
            )
        ),
        new ArrayList<ItemType>() {{
            add(FoodType.SPAGHETTI);
            add(MaterialType.WOOD);
            add(IngredientType.IRON_BAR);
        }},
        18,
        new ArrayList<ItemType>() {{
            add(MaterialType.HARD_WOOD);
            add(MaterialType.WOOD);
        }}
    );

    private final String name;
    private final Role role;
    private final Position house;
    private final List<Map.Entry<Map<ItemType, Integer>, Map<ItemType, Integer>>> quests;
    private final ArrayList<ItemType> favorites;
    private final int daysToUnlockThirdQuest;
    public final ArrayList<ItemType> gifts;

    NPCType(String name, Role role, Position house, List<Map.Entry<Map<ItemType, Integer>, Map<ItemType, Integer>>> quests,
            ArrayList<ItemType> favorites, int daysToUnlockThirdQuest, ArrayList<ItemType> gifts) {
        this.name = name;
        this.role = role;
        this.house = house;
        this.quests = quests;
        this.favorites = favorites;
        this.daysToUnlockThirdQuest = daysToUnlockThirdQuest;
        this.gifts = gifts;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public List<Map.Entry<Map<ItemType, Integer>, Map<ItemType, Integer>>> getQuests() {
        return quests;
    }

    public ArrayList<ItemType> getFavorites() {
        return favorites;
    }

    public int getDaysToUnlockThirdQuest() {
        return daysToUnlockThirdQuest;
    }

    public Position getHouse() {
        return house;
    }

    public ItemType getRandomGift() {
        int index = (new Random()).nextInt(3);
        return this.gifts.get(index);
    }
}
