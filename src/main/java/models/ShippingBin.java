package models;

import models.enums.types.FarmBuildingType;

import java.util.ArrayList;
public class ShippingBin extends FarmBuilding {
    private final ArrayList<Item> itemsToShip;
    private Position position;

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

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
