package models.farming;

import models.Item;
import models.enums.types.SeedType;

public class PlantSource extends Item {
    private SeedType seedType;
    private boolean isMixedSeed;

    public PlantSource(SeedType seedType) {
        this.seedType = seedType;
        this.isMixedSeed = seedType == SeedType.MIXED_SEEDS;
    }
}

