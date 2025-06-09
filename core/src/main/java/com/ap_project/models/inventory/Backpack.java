package com.project.models.inventory;

import com.project.models.Item;
import com.project.models.enums.types.BackpackType;

import java.util.HashMap;

public class Backpack extends Inventory {
    private BackpackType type;

    public Backpack(BackpackType type) {
        super(type.getCapacity(), type.isUnlimited());
        this.type = type;
    }

    public BackpackType getType() {
        return this.type;
    }

    public void setType(BackpackType type) {
        this.type = type;
    }

    @Override
    public HashMap<Item, Integer> getItems() {
        return super.getItems();
    }
}
