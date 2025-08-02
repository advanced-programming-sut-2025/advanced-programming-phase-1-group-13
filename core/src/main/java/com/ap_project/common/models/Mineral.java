package com.ap_project.common.models;

import com.ap_project.common.models.enums.types.MineralType;
import com.ap_project.common.models.farming.ForagingStuff;

public class Mineral extends Item implements ForagingStuff {
    private int sellPrice;
    private MineralType mineralType;
    private Position position;
    private String name;

    public Mineral(Position position) {
        // todo:
        this.position = position;
        this.mineralType = MineralType.STONE;
        this.name = (this.mineralType).getName();
    }

    @Override
    public void generate() {

    }

    public void setMineralType(MineralType mineralType) {
        this.mineralType = mineralType;
    }

    public MineralType getMineralType() {
        return mineralType;
    }

    public Position getPosition() {
        return position;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
