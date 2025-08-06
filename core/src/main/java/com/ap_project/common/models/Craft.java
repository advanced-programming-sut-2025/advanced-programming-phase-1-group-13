package com.ap_project.common.models;

import com.ap_project.common.models.enums.types.CraftType;
import com.ap_project.common.models.enums.types.IngredientType;

import java.util.HashMap;

public class Craft extends Item {
    private final CraftType craftType;
    private final String name;
    private final Position position;

    public Craft(CraftType craftType) { // TODO: remove later
        this.craftType = craftType;
        this.name = craftType.getName();
        this.position = new Position();
    }

    public Craft(CraftType craftType, Position position) {
        this.craftType = craftType;
        this.name = craftType.getName();
        this.position = position;
    }

    public HashMap<IngredientType, Integer> ingredients() {
        return this.craftType.getIngredients();
    }

    public CraftType getCraftType() {
        return this.craftType;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
