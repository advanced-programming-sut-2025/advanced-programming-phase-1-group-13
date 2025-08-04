package com.ap_project.common.models;

import com.ap_project.common.models.enums.types.GoodsType;

public class Good extends Item {
    private final GoodsType type;
    private int numberSoldToUsersToday;

    public Good(GoodsType type) {
        this.type = type;
        this.numberSoldToUsersToday = 0;
        super.name = type.getName();
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
