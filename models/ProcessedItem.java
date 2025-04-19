package models;

import models.enums.types.ProcessedItemType;

public class ProcessedItem extends Item {
    private ProcessedItemType type;

    public ProcessedItem(ProcessedItemType type) {
        this.type = type;
    }
}
