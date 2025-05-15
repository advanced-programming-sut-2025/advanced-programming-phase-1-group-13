package models.farming;

import models.Position;

public class ForagingCrop extends PlantSource implements ForagingStuff {
    private boolean isAlsoStandardCrop;
    private Position position;

    public ForagingCrop(Position pos) {
        super(null);
    }

    @Override
    public void generate() {

    }

    public Position getPosition() {
        return position;
    }
}
