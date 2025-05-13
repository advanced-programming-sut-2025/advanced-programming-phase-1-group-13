package models;

import models.enums.types.TileType;

public class Tile {
    private TileType type;
    private Position position;
    private Item itemPLacedOnTile;

    public TileType getType() {
        return this.type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void pLaceItemOnTile(Item itemPLacedOnTile) {
        this.itemPLacedOnTile = itemPLacedOnTile;
        this.setType(TileType.UNDER_AN_ITEM);
    }

    public Object getItemPlacedOnTile() {
        return this.itemPLacedOnTile;
    }

}
