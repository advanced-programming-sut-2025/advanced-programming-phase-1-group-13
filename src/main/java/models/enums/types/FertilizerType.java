package models.enums.types;

public enum FertilizerType {
    BASIC_FERTILIZER("Basic Fertilizer"),
    QUALITY_FERTILIZER("Quality Fertilizer");
    private final String name;

    FertilizerType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
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
