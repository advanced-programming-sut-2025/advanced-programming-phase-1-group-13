package models.inventory;

import models.enums.types.BackpackType;
import models.enums.types.ToolTypes;

import java.util.Map;

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
