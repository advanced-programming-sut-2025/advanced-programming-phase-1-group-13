package models.farming;

import models.Position;

public class ForagingCrop extends PlantSource implements ForagingStuff {
    private boolean isAlsoStandardCrop;

    public ForagingCrop(Position pos) {
        super();
    }

    @Override
    public void generate() {

    }
}
