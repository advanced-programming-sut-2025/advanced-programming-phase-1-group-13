package models.enums.types;

import models.NPC;

public enum ShopType {
    BLACKSMITH("Blacksmith", new NPC(NPCType.CLINT), 9, 16),
    JOJAMART("JojaMart", new NPC(NPCType.MORRIS), 9, 23),
    PIERRE_GENERAL_STORE("Pierre's General Store", new NPC(NPCType.PIERRE), 9, 17),
    CARPENTER_SHOP("Carpenter's Shop", new NPC(NPCType.ROBIN), 9, 20),
    FISH_SHOP("Fish Shop", new NPC(NPCType.WILLY), 9, 17),
    MARNIE_RANCH("Marnie's Ranch", new NPC(NPCType.MARNIE), 9, 16),
    THE_STARDROP_SALOON("The Stardrop Saloon", new NPC(NPCType.GUS), 12, 24);

    private final String name;
    private final NPC owner;
    private final int startHour;
    private final int endHour;

    ShopType(String name, NPC owner, int startHour, int endHour) {
        this.name = name;
        this.owner = owner;
        this.startHour = startHour;
        this.endHour = endHour;
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

    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
}
