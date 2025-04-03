package models;

import models.enums.types.ItemType;

public class Item {
    ItemType type;

    public Item(ItemType type) {
        this.type = type;
    }

    public ItemType getType() {
        return type;
    }
}
