package models;

import models.enums.types.ItemType;

public abstract class Item {
    //    private ItemType type;
    private boolean isSellable;
    private boolean isPurchasable;
    private int price;
    private String name;

    public Item() {
        // TODO: constructor
    }

    public String getName() {
        return this.name;
    }

    public boolean isSellable() {
        return this.isSellable;
    }

    public void setSellable(boolean sellable) {
        isSellable = sellable;
    }

    public boolean isPurchasable() {
        return this.isPurchasable;
    }

    public void setPurchasable(boolean purchasable) {
        isPurchasable = purchasable;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static ItemType getItemTypeByItemName(String itemName) {
        // TODO
        return null;
    }

    public static Item getItemByItemName(String itemName) {
        // TODO
        return null;
    }

    @Override
    public String toString() {
        return this.name;
    }
}
