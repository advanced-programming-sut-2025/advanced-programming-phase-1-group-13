package com.ap_project.common.models.enums.types;

public enum GameMenuType {
    INVENTORY("Inventory"),
    SKILLS("Skills"),
    SOCIAL("Social"),
    MAP("Map"),
    CRAFTING("Crafting"),
    EXIT("Exit");

    private final String name;

    GameMenuType(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
