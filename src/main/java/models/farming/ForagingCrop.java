package models.farming;

import models.Item;
import models.Position;
import models.enums.types.ForagingCropType;

public class ForagingCrop extends Item implements ForagingStuff {
    private Position position;
    private final ForagingCropType foragingCropType;
    private String name;

    public ForagingCrop(ForagingCropType foragingCropType, Position position) {
        this.foragingCropType = foragingCropType;
        this.position = position;
        this.name = foragingCropType.getName();
    }

    public ForagingCrop(ForagingCropType foragingCropType) {
        this.foragingCropType = foragingCropType;
    }

    @Override
    public void generate() {
    }

    public Position getPosition() {
        return position;
    }

    public ForagingCropType getForagingCropType() {
        return this.foragingCropType;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
