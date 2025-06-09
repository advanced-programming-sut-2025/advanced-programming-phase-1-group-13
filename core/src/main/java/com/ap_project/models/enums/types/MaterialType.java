package com.ap_project.models.enums.types;

public enum MaterialType implements ItemType {
    WOOD("Wood"),
    HARD_WOOD("Hard wood"),
    STONE("Stone");

    private final String name;

    MaterialType(String name) {
        this.name = name;
    }

    public static MaterialType getMaterialTypeByName(String name) {
        for (MaterialType materialType : MaterialType.values()) {
            if (materialType.getName().equals(name)) {
                return materialType;
            }
        }
        return null;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
