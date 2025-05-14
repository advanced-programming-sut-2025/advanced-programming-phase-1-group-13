package models;

import models.enums.types.FarmBuildingType;

public class FarmBuilding {
    private final FarmBuildingType farmBuildingType;
    private final Position positionOfUpperLeftCorner;
    private final int width;
    private final int length;

    public FarmBuilding(FarmBuildingType farmBuildingType, Position positionOfUpperLeftCorner) {
        this.farmBuildingType = farmBuildingType;
        this.positionOfUpperLeftCorner = positionOfUpperLeftCorner;
        this.width = farmBuildingType.getWidth();
        this.length = farmBuildingType.getLength();
    }

    public FarmBuildingType getFarmBuildingType() {
        return farmBuildingType;
    }

    public Position getPositionOfUpperLeftCorner() {
        return positionOfUpperLeftCorner;
    }

    public int getWidth() {
        return width;
    }

    public int getLength() {
        return length;
    }
}

