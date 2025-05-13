package models;

import models.enums.types.MineralType;
import models.farming.ForagingStuff;

public class Mineral implements ForagingStuff {
    private int sellPrice;
    private MineralType mineralType;
    private Position position;

    public Mineral(Position position) {
    }

    @Override
    public void generate() {

    }

    public void setMineralType(MineralType mineralType) {
        this.mineralType = mineralType;
    }

    public Position getPosition() {
        return position;
    }
}