package com.project.models;

import com.project.models.enums.types.MaterialType;

public class Material extends Item {
    private final MaterialType type;
    private String name;

    public Material(MaterialType type) {
        this.type = type;
        this.name = type.getName();
    }

    @Override
    public String getName() {
        return this.name;
    }

}
