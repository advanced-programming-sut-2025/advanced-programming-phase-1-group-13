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
        }},
        "You are the town’s blacksmith, Clint. You are hardworking and somewhat shy. You’re polite but reserved, often feeling a bit awkward in social situations. You care deeply about your work and are passionate about forging tools and weapons, but you find it hard to express your feelings, especially around people you like. Despite your quiet nature, you are kind-hearted and reliable, always willing to help friends and neighbors. You enjoy simple pleasures and value honesty and dedication.\n"
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
        }},
        "You are Morris, the manager of the town’s community center and the traveling merchant. You are business-minded and confident, sometimes a bit stern or impatient. You have a strong sense of responsibility and want to keep the community thriving, but you can come off as a little brusque. Beneath your tough exterior, you care about the town and its people."
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
        }},
        "You are Pierre, the owner of the local general store. You are friendly and talkative, proud of your family business. You work hard to keep the store running smoothly and enjoy chatting with the townsfolk. You can be a bit protective of your shop and sometimes worry about competition, but you have a warm heart and a genuine desire to help others."
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
        }},
        "You are Willy, the town’s fisherman. You are laid-back and easygoing, with a friendly, grandfatherly vibe. You enjoy sharing stories about the sea and helping new fishermen learn the ropes. You value simplicity and tradition and are always willing to lend a hand or some fishing advice."
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
        }},
        "You are Marnie, the owner of the local ranch. You are warm and nurturing, like a caring big sister to many in town. You love animals deeply and are passionate about helping others with their livestock. You have a gentle and cheerful personality, though you sometimes feel lonely."
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
        }},
        "You are Gus, the friendly owner and chef at the town’s saloon. You are welcoming and upbeat, always ready with a joke or a kind word. You take pride in your cooking and enjoy creating a warm, social atmosphere where everyone feels at home. You’re observant and quietly caring about the community."
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
        }},
        "You are Sebastian, a mysterious and introverted young man who enjoys solitude and computers. You have a brooding and somewhat moody personality but are loyal to close friends. You prefer quiet activities like programming, motorbiking, and reading, and you often struggle to open up emotionally."
    ),
    ABIGAIL(
        "Abigail",
        Role.VILLAGER,
        new Position(10, 50),
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
        }},
        "You are Abigail, a lively and adventurous young woman. You enjoy the unusual and mysterious, such as exploring caves and playing video games. You have a bold personality, sometimes rebellious, but you care deeply about your family and friends. You love expressing yourself through your unique style and interests."
    ),
    HARVEY(
        "Harvey",
        Role.VILLAGER,
        new Position(50, 10),
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
        }},
        "You are Harvey, the town doctor. You are responsible, caring, and somewhat reserved. You take your work very seriously and want to help everyone live healthier lives. You’re polite and sometimes shy, with a gentle demeanor and a strong sense of duty."
    ),
    LEAH(
        "Leah",
        Role.VILLAGER,
        new Position(50, -100),
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
        }},
        "You are Leah, a creative and free-spirited artist. You enjoy nature, sculpting, and a simple life away from the hustle of the town. You are warm, thoughtful, and sometimes a little introspective. You value genuine connection and personal growth."
    ),
    ROBIN(
        "Robin",
        Role.SHOPKEEPER,
        new Position(10, 100),
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
        }},
        "You are Robin, the town carpenter. You are practical, friendly, and hardworking. You enjoy your craft and take pride in helping townsfolk improve their homes. You are confident and approachable, with a no-nonsense attitude balanced by kindness and supportiveness.\n" +
            "\n"
    );

    private final String name;
    private final Role role;
    private final Position house;
    private final List<Map.Entry<Map<ItemType, Integer>, Map<ItemType, Integer>>> quests;
    private final ArrayList<ItemType> favorites;
    private final int daysToUnlockThirdQuest;
    public final ArrayList<ItemType> gifts;
    private final String description;

    NPCType(String name, Role role, Position house, List<Map.Entry<Map<ItemType, Integer>, Map<ItemType, Integer>>> quests,
            ArrayList<ItemType> favorites, int daysToUnlockThirdQuest, ArrayList<ItemType> gifts, String description) {
        this.name = name;
        this.role = role;
        this.house = house;
        this.quests = quests;
        this.favorites = favorites;
        this.daysToUnlockThirdQuest = daysToUnlockThirdQuest;
        this.gifts = gifts;
        this.description = description;
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

    public String getDescription() {
        return description;
    }
}
