package models.enums.types;

public enum MineralType implements ItemType {
    STONE("Stone"),
    COPPER("Copper"),
    IRON("Iron"),
    GOLD("Gold"),
    IRIDIUM("Iridium"),

    DIAMOND("Diamond"),
    QUARTZ("Quartz"),

    IRON_ORE("Iron ore"),
    IRON_BAR("Iron bar"),
    COIN("Coin"),
    GOLD_COIN("Gold coin"),
    GOLD_BAR("Gold bar");

    private final String name;

    MineralType(String name) {
        this.name = name;
    }

    public static MineralType getMineralTypeByName(String name) {
        for (MineralType mineralType : MineralType.values()) {
            if (mineralType.getName().equals(name)) {
                return mineralType;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
