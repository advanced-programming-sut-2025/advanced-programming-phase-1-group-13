package com.ap_project.models;

import com.ap_project.models.enums.types.ProcessedItemType;

public class ProcessedItem extends Item {
    private final ProcessedItemType type;
    private String name;

    public ProcessedItem(ProcessedItemType type) {
        this.type = type;
        this.name = type.getName();
    }

    @Override
    public String getName() {
        return this.name;
    }
}
