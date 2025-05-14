package models;

import models.enums.types.MaterialType;

public class Material extends Item {
    private final MaterialType type;

    public Material(MaterialType type) {
        this.type = type;
    }
}