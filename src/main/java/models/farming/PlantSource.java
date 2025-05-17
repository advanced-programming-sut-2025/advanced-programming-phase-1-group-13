package models.farming;

import models.Item;
import models.enums.types.ItemType;
import models.enums.types.MixedSeedsType;
import models.enums.types.SeedType;
import models.enums.types.TreeSourceType;

public class PlantSource extends Item {
    private SeedType seedType;
    private MixedSeedsType mixedSeedsType;
    private TreeSourceType treeSourceType;
    private boolean isMixedSeed;
    private boolean isTreeSeed;


    public PlantSource(ItemType seedType) {
        if (seedType instanceof MixedSeedsType) {
            this.isMixedSeed = true;
            this.mixedSeedsType = (MixedSeedsType) seedType;
            this.seedType = SeedType.MIXED_SEEDS;
        } else if (seedType instanceof TreeSourceType) {
            this.isTreeSeed = true;
            this.treeSourceType = (TreeSourceType) seedType;
            this.seedType = SeedType.TREE_SOURCE_SEED;
            this.isMixedSeed = false;
            this.mixedSeedsType = null;
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

