package models.enums.types;

import models.farming.Crop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public enum NPCType {
    CLINT(
            "Clint",
            Role.SHOPKEEPER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>(),
            new ArrayList<ItemType>(),
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
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>(),
            new ArrayList<ItemType>(),
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
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>(),
            new ArrayList<ItemType>(),
            20,
            new ArrayList<ItemType>() {{
                add(GoodsType.STARFRUIT_SEEDS);
                add(GoodsType.JOJA_COLA);
                add(GoodsType.COFFEE);
            }}
    ),
    ROBIN(
            "Robin",
            Role.SHOPKEEPER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>() {{
                put(
                        new HashMap<ItemType, Integer>() {{
                            put(MaterialType.WOOD, 80);
                            put(MineralType.IRON_BAR, 10);
                            put(MaterialType.WOOD, 1000);
                        }},
                        new HashMap<ItemType, Integer>() {{
                            put(MineralType.GOLD_COIN, 1000);
                            put(CraftType.BEE_HOUSE, 3);
                            put(MineralType.COIN, 25000);
                        }}
                );
            }},
            new ArrayList<ItemType>() {{
                add(FoodType.SPAGHETTI);
                add(MaterialType.WOOD);
                add(MineralType.IRON_BAR);
            }},
            18,
            new ArrayList<ItemType>() {{
                add(MaterialType.HARD_WOOD);
                add(MaterialType.WOOD);
            }}
    ),
    WILLY(
            "Willy",
            Role.SHOPKEEPER,
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>(),
            new ArrayList<ItemType>(),
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
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>(),
            new ArrayList<ItemType>(),
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
            new HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>>(),
            new ArrayList<ItemType>(),
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
                            put(ToolType.WATERING_CAN, 1);
                        }}
                );
            }},
            new ArrayList<ItemType>() {{
                add(MineralType.STONE);
                add(MineralType.IRON_ORE);
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
                            put(CraftType.SCARECROW, 3);
                        }}
                );
            }},
            new ArrayList<ItemType>() {{
                add(FoodType.SALAD);
                add(CropType.GRAPE);
                add(ProcessedItemType.WINE);
            }},
            14,
            new ArrayList<ItemType>() {{
                add(CropType.BLACKBERRY);
                add(FoodType.SALAD);
                add(ProcessedItemType.MEAD);
            }}
    )
    ;

    private final String name;
    private final Role role;
    private final HashMap<HashMap<ItemType, Integer>, // requests
            HashMap<ItemType, Integer> // rewards
            > quests;
    private final ArrayList<ItemType> favorites;
    private final int daysToUnlockThirdQuest;
    public final ArrayList<ItemType> gifts;

    NPCType(String name, Role role, HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>> quests,
            ArrayList<ItemType> favorites, int daysToUnlockThirdQuest, ArrayList<ItemType> gifts) {
        this.name = name;
        this.role = role;
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

    public HashMap<HashMap<ItemType, Integer>, // requests
            HashMap<ItemType, Integer> // rewards
            > getQuests() {
        return quests;
    }

    public ArrayList<ItemType> getFavorites() {
        return favorites;
    }

    public int getDaysToUnlockThirdQuest() {
        return daysToUnlockThirdQuest;
    }

    public static String questString(int index, NPCType npcType, int friendshipLevel) {
        HashMap<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>> quests = npcType.getQuests();
        int questIndex = 0;

        for (Map.Entry<HashMap<ItemType, Integer>, HashMap<ItemType, Integer>> entry : quests.entrySet()) {
            if (questIndex == index) {
                StringBuilder message = new StringBuilder();
                message.append(index + 1).append(". Request:");

                for (Map.Entry<ItemType, Integer> request : entry.getKey().entrySet()) {
                    message.append(" ").append(request.getValue()).append(" ").append(request.getKey().getName()).append("\n   ");
                }

                message.append("Reward:");
                for (Map.Entry<ItemType, Integer> reward : entry.getValue().entrySet()) {
                    int rewardAmount = reward.getValue();
                    if (friendshipLevel == 2) {
                        rewardAmount *= 2;
                    }
                    message.append(" ").append(rewardAmount).append(" ").append(reward.getKey().getName());
                }

                return message.toString();
            }
            questIndex++;
        }

        return "";
    }

    public ItemType getRandomGift() {
        int index = (new Random()).nextInt(3);
        return this.gifts.get(index);
    }

    public ItemType getRequestItemType(int i) {
        int index = 0;
        for (HashMap<ItemType, Integer> requestMap : quests.keySet()) {
            for (ItemType itemType : requestMap.keySet()) {
                if (index == i) {
                    return itemType;
                }
                index++;
            }
        }
        return null;
    }

    public int getRequestQuantity(int i) {
        int index = 0;
        for (HashMap<ItemType, Integer> requestMap : quests.keySet()) {
            for (Integer quantity : requestMap.values()) {
                if (index == i) {
                    return quantity;
                }
                index++;
            }
        }
        return -1;
    }

    public ItemType getRewardItemType(int i) {
        int index = 0;
        for (HashMap<ItemType, Integer> rewardMap : quests.values()) {
            for (ItemType itemType : rewardMap.keySet()) {
                if (index == i) {
                    return itemType;
                }
                index++;
            }
        }
        return null;
    }

    public Integer getRewardQuantity(int i) {
        int index = 0;
        if (quests.isEmpty()) {
            return 0;
        }
        for (HashMap<ItemType, Integer> rewardMap : quests.values()) {
            for (int quantity : rewardMap.values()) {
                if (index == i) {
                    return quantity;
                }
                index++;
            }
        }
        return 0;
    }

}
