package models.enums.types;

import models.NPC;

public enum ShopType {
    BLACKSMITH("Blacksmith"),
    MARNIE_RANCH("Marnie's Ranch"),
    THE_STARDROP_SALOON("The Stardrop Saloon"),
    CARPENTER_SHOP("Carpenter's Shop"),
    JOJAMART("Jojamart"),
    PIERRE_GENERAL_STORE("Pierre's General Store"),
    FISH_SHOP("Fish Shop"),
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
