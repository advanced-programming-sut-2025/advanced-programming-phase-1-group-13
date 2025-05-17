package models.farming;

import models.Position;
import models.enums.types.ForagingCropType;

public class ForagingCrop extends Crop implements ForagingStuff {
    private Position position;
    private final ForagingCropType foragingCropType;

    public ForagingCrop(ForagingCropType foragingCropType, Position position) {
        super(foragingCropType);
        this.foragingCropType = foragingCropType;
        this.position = position;
    }

    @Override
    public void generate() {

    }

    public Position getPosition() {
        return position;
    }

    public ForagingCropType getForagingCropType() {
        return foragingCropType;
    }
}
