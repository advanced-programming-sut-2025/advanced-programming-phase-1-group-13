package com.ap_project.common.models.enums.types;

public enum FlowerType implements ItemType {
    DANDELION("Dandelion", true),
    SWEET_PEA("Sweet pea", true),
    CROCUS("Crocus", true),
    TULIP("Tulip", false),
    BLUE_JAZZ("Blue jazz", false),
    SUMMER_SPANGLE("Summer spangle", false),
    POPPY("Poppy", false),
    SUNFLOWER("Sunflower", false),
    FAIRY_ROSE("Fairy rose", false);

    private final String name;
    private final Boolean isForaging;

    FlowerType(String name, Boolean isForaging) {
        this.name = name;
        this.isForaging = isForaging;
    }

    public static FlowerType getFlowerTypeByName(String name) {
        for (FlowerType flowerType : FlowerType.values()) {
            if (flowerType.getName().equals(name)) {
                return flowerType;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
