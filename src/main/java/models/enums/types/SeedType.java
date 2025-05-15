package models.enums.types;

public enum SeedType implements ItemType {
    JAZZ_SEEDS("Jazz Seeds"),
    CARROT_SEEDS("Carrot Seeds"),
    CAULIFLOWER_SEEDS("Cauliflower Seeds"),
    COFFEE_BEAN("Coffee Bean"),
    GARLIC_SEEDS("Garlic Seeds"),
    BEAN_STARTER("Bean Starter"),
    KALE_SEEDS("Kale Seeds"),
    PARSNIP_SEEDS("Parsnip Seeds"),
    POTATO_SEEDS("Potato Seeds"),
    RHUBARB_SEEDS("Rhubarb Seeds"),
    STRAWBERRY_SEEDS("Strawberry Seeds"),
    TULIP_BULB("Tulip Bulb"),
    RICE_SHOOT("Rice Shoot"),
    BLUEBERRY_SEEDS("Blueberry Seeds"),
    CORN_SEEDS("Corn Seeds"),
    HOPS_STARTER("Hops Starter"),
    PEPPER_SEEDS("Pepper Seeds"),
    MELON_SEEDS("Melon Seeds"),
    POPPY_SEEDS("Poppy Seeds"),
    RADISH_SEEDS("Radish Seeds"),
    RED_CABBAGE_SEEDS("Red Cabbage Seeds"),
    STARFRUIT_SEEDS("Starfruit Seeds"),
    SPANGLE_SEEDS("Spangle Seeds"),
    SUMMER_SQUASH_SEEDS("Summer Squash Seeds"),
    SUNFLOWER_SEEDS("Sunflower Seeds"),
    TOMATO_SEEDS("Tomato Seeds"),
    WHEAT_SEEDS("Wheat Seeds"),
    AMARANTH_SEEDS("Amaranth Seeds"),
    ARTICHOKE_SEEDS("Artichoke Seeds"),
    BEET_SEEDS("Beet Seeds"),
    BOK_CHOY_SEEDS("Bok Choy Seeds"),
    BROCCOLI_SEEDS("Broccoli Seeds"),
    CRANBERRY_SEEDS("Cranberry Seeds"),
    EGGPLANT_SEEDS("Eggplant Seeds"),
    FAIRY_SEEDS("Fairy Seeds"),
    GRAPE_STARTER("Grape Starter"),
    PUMPKIN_SEEDS("Pumpkin Seeds"),
    YAM_SEEDS("Yam Seeds"),
    RARE_SEED("Rare Seed"),
    POWDERMELON_SEEDS("Powdermelon Seeds"),
    ANCIENT_SEEDS("Ancient Seeds"),
    MIXED_SEEDS("Mixed Seeds");

    private final String name;

    SeedType(String name) {
        this.name = name;
    }



    public static SeedType getSeedByName(String name) {
        if (name == null || name.trim().isEmpty()) {
            return null;
        }

        String normalized = name.trim().toUpperCase().replace(" ", "_");

        try {
            return SeedType.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            for (SeedType seedType : values()) {
                if (seedType.name.equalsIgnoreCase(name.trim())) {
                    return seedType;
                }
            }
            return null;
        }
    }

    public static String getAvailableSeedNames() {
        StringBuilder sb = new StringBuilder();
        for (SeedType seedType : values()) {
            sb.append(seedType.name).append(", ");
        }
        if (!sb.isEmpty()) {
            sb.setLength(sb.length() - 2);
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return name;
    }

    @Override
    public String getName() {
        return name;
    }
}