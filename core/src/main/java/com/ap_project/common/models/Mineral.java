package com.ap_project.common.models;

import com.ap_project.common.models.enums.types.MineralType;
import com.ap_project.common.models.farming.ForagingStuff;

public class Mineral extends Item implements ForagingStuff {
    private final MineralType mineralType;
    private Position position;

    public Mineral(MineralType mineralType, Position position) {
        this.position = position;
        this.mineralType = mineralType;
        this.setPrice(mineralType.getSellPrice());
        this.name = mineralType.getName();
    }

    public Mineral(MineralType mineralType) {
        this.mineralType = mineralType;
        this.setPrice(mineralType.getSellPrice());
        this.name = mineralType.getName();
    }

    @Override
    public void generate() {

    }

    public MineralType getMineralType() {
        return mineralType;
    }

    public Position getPosition() {
        return position;
    }
}
