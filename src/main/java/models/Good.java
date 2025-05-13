package models;

import models.enums.types.GoodsType;

import java.util.HashMap;

public class Good extends Item {
    private final GoodsType type;
    private int numberSoldToUsersToday;

    public Good(GoodsType type) {
        this.type = type;
        this.numberSoldToUsersToday = 0;
    }

    public GoodsType getType() {
        return type;
    }

    public int getNumberSoldToUsersToday() {
        return numberSoldToUsersToday;
    }

    public void setNumberSoldToUsersToday(int numberSoldToUsersToday) {
        this.numberSoldToUsersToday = numberSoldToUsersToday;
    }
}
