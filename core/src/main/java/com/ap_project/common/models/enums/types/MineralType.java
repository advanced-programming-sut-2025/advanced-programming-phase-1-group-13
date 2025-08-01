package com.ap_project.common.models.enums.types;

public enum MineralType implements ItemType {
    QUARTZ("Quartz",
            "A clear crystal commonly found in caves and mines.",
            25),
    EARTH_CRYSTAL("Earth Crystal",
            "A resinous substance found near the surface.",
            50),
    FROZEN_TEAR("Frozen Tear",
            "A crystal fabled to be the frozen tears of a yeti.",
            75),
    FIRE_QUARTZ("Fire Quartz",
            "A glowing red crystal commonly found near hot lava.",
            100),
    EMERALD("Emerald",
            "A precious stone with a brilliant green color.",
            250),
    AQUAMARINE("Aquamarine",
            "A shimmery blue-green gem.",
            180),
    RUBY("Ruby",
            "A precious stone that is sought after for its rich color and beautiful luster.",
            250),
    AMETHYST("Amethyst",
            "A purple variant of quartz.",
            100),
    TOPAZ("Topaz",
            "Fairly common but still prized for its beauty.",
            80),
    JADE("Jade",
            "A pale green ornamental stone.",
            200),
    DIAMOND("Diamond",
            "A rare and valuable gem.",
            750),
    PRISMATIC_SHARD("Prismatic Shard",
            "A very rare and powerful substance with unknown origins.",
            2000),

    COPPER("Copper",
            "A common ore that can be smelted into bars.",
            5),
    IRON("Iron",
            "A fairly common ore that can be smelted into bars.",
            10),
    GOLD("Gold",
            "A precious ore that can be smelted into bars.",
            25),
    IRIDIUM("Iridium",
            "An exotic ore with many curious properties. Can be smelted into bars.",
            100),
    COAL("Coal",
            "A combustible rock that is useful for crafting and smelting.",
            15),

    STONE("Stone",
            "A common material with many uses in crafting and building.",
            0); // or ROCK

    private final String name;
    private final String description;
    private final int sellPrice;

    MineralType(String name, String description, int sellPrice) {
        this.name = name;
        this.description = description;
        this.sellPrice = sellPrice;
    }

    public static MineralType getMineralTypeByName(String name) {
        for (MineralType mineralType : MineralType.values()) {
            if (mineralType.getName().equalsIgnoreCase(name)) {
                return mineralType;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public String getDescription() {
        return description;
    }

    public int getSellPrice() {
        return sellPrice;
    }
}
