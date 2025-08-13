package com.ap_project.common.models.inventory;

import com.ap_project.common.models.Item;
import com.ap_project.common.models.enums.types.BackpackType;

import java.util.HashMap;

public class Backpack extends Inventory {
    private BackpackType type;

    public Backpack(BackpackType type) {
        super(type.getCapacity(), type.isUnlimited());
        this.type = type;
        this.isCapacityUnlimited = true; // TODO: remove later
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
