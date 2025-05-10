package models.enums.types;

public enum MaterialType implements ItemType {
    WOOD("Wood"),
    HARD_WOOD("Hard wood"),
    STONE("Stone");

    private final String name;

    MaterialType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
