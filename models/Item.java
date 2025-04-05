package models;

import models.enums.types.ProcessedItemType;

public abstract class Item {
    private boolean isSellable;
    private boolean isPurchasable;
    private int price;

    public boolean isSellable() {
        return isSellable;
    }

    public void setSellable(boolean sellable) {
        isSellable = sellable;
    }

    public boolean isPurchasable() {
        return isPurchasable;
    }

    public void setPurchasable(boolean purchasable) {
        isPurchasable = purchasable;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
