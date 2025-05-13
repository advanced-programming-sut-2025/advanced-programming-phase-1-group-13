package models;

import models.enums.types.MineralType;
import models.farming.ForagingStuff;

public class Mineral extends Item implements ForagingStuff {
    private int sellPrice;
    private MineralType mineralType;
    private String name;

    public Mineral(Position position) {
    }

    public Mineral(MineralType mineralType) {
        this.mineralType = mineralType;
        this.name = mineralType.getName();
    }

    public int getSellPrice() {
        return sellPrice;
    }

    public MineralType getMineralType() {
        return mineralType;
    }

    @Override
    public void generate() {

    }
}