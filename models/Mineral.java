package models;

import models.enums.types.MineralType;
import models.farming.ForagingStuff;

public class Mineral implements ForagingStuff {
    private int sellPrice;
    private MineralType mineralType;

    @Override
    public void generate() {

    }
}