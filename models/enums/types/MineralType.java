package models.enums.types;

public enum MineralType implements ItemType {
    DIAMOND("Diamond"),
    IRON("Iron"),
    IRON_ORE("Iron ore"),
    IRON_BAR("Iron bar"),
    COIN("Coin"),
    GOLD_COIN("Gold coin"),
    GOLD_BAR("Gold bar"),
    STONE("Stone"),
    QUARTZ("Quartz");

    private final String name;

    MineralType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
