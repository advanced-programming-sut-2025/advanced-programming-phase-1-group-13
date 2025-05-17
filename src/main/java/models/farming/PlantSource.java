package models.farming;

import models.Item;
import models.enums.types.ItemType;
import models.enums.types.MixedSeedsType;
import models.enums.types.SeedType;

public class PlantSource extends Item {
    private SeedType seedType;
    private boolean isMixedSeed;
    private MixedSeedsType mixedSeedsType;

    public PlantSource(ItemType seedType) {
        if (seedType instanceof MixedSeedsType) {
            this.isMixedSeed = true;
            this.mixedSeedsType = (MixedSeedsType) seedType;
            this.seedType = SeedType.MIXED_SEEDS;
        } else {
            this.seedType = (SeedType) seedType;
            this.isMixedSeed = false;
            this.mixedSeedsType = null;
        }
    }

    public SeedType getSeedType() {
        return seedType;
    }

    public MixedSeedsType getMixedSeedsType() {
        return mixedSeedsType;
    }

    public boolean isMixedSeed() {
        return isMixedSeed;
    }
}

