package com.ap_project.common.models;

import com.ap_project.common.models.enums.types.FarmBuildingType;

import java.util.ArrayList;
public class ShippingBin extends FarmBuilding {
    private final ArrayList<Item> itemsToShip;

    public ShippingBin(FarmBuildingType farmBuildingType, Position positionOfUpperLeftCorner) {
        super(farmBuildingType, positionOfUpperLeftCorner);
        this.itemsToShip = new ArrayList<>();
    }

    public ArrayList<Item> getItemsToShip() {
        return itemsToShip;
    }

    public void addItemToShip(Item item) {
        this.itemsToShip.add(item);
    }
}
