package models.inventory;

import models.enums.types.BackpackType;

public class Backpack extends Inventory {
    private BackpackType type;

    public Backpack(BackpackType type) {
        super(type.getCapacity(), type.isUnlimited());
        this.type = type;
    }

    public BackpackType getType() {
        return this.type;
    }
}
