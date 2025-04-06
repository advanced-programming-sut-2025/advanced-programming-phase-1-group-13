package models;

import models.enums.types.TileType;

public class Tile {
    private TileType type;
    private Position position;

    public TileType getType() {
        return type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}
