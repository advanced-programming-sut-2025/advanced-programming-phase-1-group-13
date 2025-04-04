package models.enums.types;

public enum IngredientType {
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
    MIDNIGHT_CARP("Midnight Carp");

    private final String name;

    IngredientType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
