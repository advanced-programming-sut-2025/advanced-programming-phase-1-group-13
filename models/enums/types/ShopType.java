package models.enums.types;

import models.NPC;

public enum ShopType {
    BLACKSMITH("Blacksmith", new NPC(NPCType.CLINT)),
    MARNIE_RANCH("Marnie's Ranch", new NPC(NPCType.MARNIE)),
    THE_STARDROP_SALOON("The Stardrop Saloon", new NPC(NPCType.GUS)),
    CARPENTER_SHOP("Carpenter's Shop", new NPC(NPCType.ROBIN)),
    JOJAMART("JojaMart", new NPC(NPCType.MORRIS)),
    PIERRE_GENERAL_STORE("Pierre's General Store", new NPC(NPCType.PIERRE)),
    FISH_SHOP("Fish Shop", new NPC(NPCType.WILLY)),
    ;

    private final String name;
    private final NPC owner;

    ShopType(String name, NPC owner) {
        this.name = name;
        this.owner = owner;
    }

    public static ShopType getShopTypeByName(String name) {
        return switch (name) {
            case "Blacksmith" -> BLACKSMITH;
            case "Marnie's Ranch" -> MARNIE_RANCH;
            case "TheStardrop Saloon" -> THE_STARDROP_SALOON;
            case "Carpenter's Shop" -> CARPENTER_SHOP;
            case "JojaMart" -> JOJAMART;
            case "Jojamart" -> JOJAMART;
            case "Pierre's General Store" -> PIERRE_GENERAL_STORE;
            case "Fish Shop" -> FISH_SHOP;
            default -> null;
        };
    }

    public String getName() {
        return name;
    }

    public NPC getOwner() {
        return owner;
    }
}
