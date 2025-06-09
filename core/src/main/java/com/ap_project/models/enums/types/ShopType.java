package com.project.models.enums.types;

import com.project.models.NPC;

public enum ShopType {
    BLACKSMITH("Blacksmith", NPCType.CLINT, 9, 16),
    JOJAMART("JojaMart", NPCType.MORRIS, 9, 23),
    PIERRE_GENERAL_STORE("Pierre's General Store", NPCType.PIERRE, 9, 17),
    CARPENTER_SHOP("Carpenter's Shop", NPCType.ROBIN, 9, 20),
    FISH_SHOP("Fish Shop", NPCType.WILLY, 9, 17),
    MARNIE_RANCH("Marnie's Ranch", NPCType.MARNIE, 9, 16),
    THE_STARDROP_SALOON("The Stardrop Saloon", NPCType.GUS, 12, 24);

    private final String name;
    private final NPCType ownerType;
    private final int startHour;
    private final int endHour;

    ShopType(String name, NPCType ownerType, int startHour, int endHour) {
        this.name = name;
        this.ownerType = ownerType;
        this.startHour = startHour;
        this.endHour = endHour;
    }

    public String getName() {
        return name;
    }

    public NPC getOwner() {
        return new NPC(ownerType);
    }

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }

    public ShopType getShopTypeByName(String name) {
        for (ShopType shopType : ShopType.values()) {
            if (shopType.getName().equals(name)) {
                return shopType;
            }
        }
        return null;
    }
}
