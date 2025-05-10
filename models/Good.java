package models;

import models.enums.types.GoodsType;

public class Good extends Item {
    private GoodsType type;

    public Good(GoodsType type) {
        this.type = type;
    }
}
