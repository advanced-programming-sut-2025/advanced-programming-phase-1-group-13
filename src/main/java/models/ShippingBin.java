package models;

import models.enums.types.FarmBuildingType;

import java.util.ArrayList;
public class ShippingBin extends FarmBuilding {
    private ArrayList<Item> itemsToShip;

    public ShippingBin(FarmBuildingType farmBuildingType, Position positionOfUpperLeftCorner) {
        super(farmBuildingType, positionOfUpperLeftCorner);
        this.itemsToShip = new ArrayList<>();
    }

    public ArrayList<Item> getItemsToShip() {
        return itemsToShip;
    }

    public void setItemsToShip(ArrayList<Item> itemsToShip) {
        this.itemsToShip = itemsToShip;
    }
}
