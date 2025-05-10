package models.enums.types;

public enum IngredientType implements ItemType {
    EGG("Egg"),
    SARDINE("Sardine"),
    SALMON("Salmon"),
    WHEAT("Wheat"),
    LEEK("Leek"),
    DANDELION("Dandelion"),
    MILK("Milk"),
    PUMPKIN("Pumpkin"),
    WHEAT_FLOUR("Wheat Flour"),
    SUGAR("Sugar"),
    TOMATO("Tomato"),
    CHEESE("Cheese"),
    CORN("Corn"),
    OIL("Oil"),
    OMELET("Omelet"),
    BREAD("Bread"),
    HASH_BROWNS("Hash browns"),
    ANY_FISH("Any Fish"),
    RICE("Rice"),
    FIBER("Fiber"),
    COFFEE("Coffee"),
    POTATO("Potato"),
    BLUEBERRY("Blueberry"),
    MELON("Melon"),
    APRICOT("Apricot"),
    RED_CABBAGE("Red Cabbage"),
    RADISH("Radish"),
    AMARANTH("Amaranth"),
    KALE("Kale"),
    BEET("Beet"),
    PARSNIP("Parsnip"),
    CARROT("Carrot"),
    EGGPLANT("Eggplant"),
    FLOUNDER("Flounder"),
    MIDNIGHT_CARP("Midnight Carp"),

    /*
    (below) recently added from:
    (https://docs.google.com/spreadsheets/d/1f9TNkQJ_rIDPWR33TeHrxzIzmzNIhkKtVLPU6921JSg/edit?gid=0#gid=0)
    */
    COAL("Coal"),
    COPPER_ORE("Copper Ore"),
    IRON_ORE("Iron Ore"),
    GOLD_ORE("Gold Ore"),
    IRIDIUM_ORE("Iridium Ore"),
    COPPER_BAR("Copper Bar"),
    IRON_BAR("Iron Bar"),
    GOLD_BAR("Gold Bar"),
    IRIDIUM_BAR("Iridium Bar"),
    WOOD("Wood"),
    STONE("Stone"),
    ACORN("Acorn"),
    MAPLE_SEED("Maple Seed"),
    PINE_CONE("Pine Cone"),
    MAHOGANY_SEED("Mahogany Seed"),
    ;

    private final String name;

    IngredientType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
