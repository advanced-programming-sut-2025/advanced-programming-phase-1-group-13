package models;

import models.enums.types.MineralType;
import models.farming.ForagingStuff;

public class Mineral implements ForagingStuff {
    private int sellPrice;
    private MineralType mineralType;

    public Mineral(Position position) {
    }

    @Override
    public void generate() {

    }
}