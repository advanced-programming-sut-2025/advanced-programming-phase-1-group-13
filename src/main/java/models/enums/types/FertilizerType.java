package models.enums.types;

public enum FertilizerType implements ItemType {
    BASIC_FERTILIZER("Basic Fertilizer"),
    QUALITY_FERTILIZER("Quality Fertilizer");
    private final String name;

    FertilizerType(String name) {
        this.name = name;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public static FertilizerType getFertilizerTypeByName(String name) {
        for (FertilizerType type : FertilizerType.values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }
}
