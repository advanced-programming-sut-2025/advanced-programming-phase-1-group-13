package com.project.models.farming;

import com.project.models.Item;
import com.project.models.enums.types.ItemType;
import com.project.models.enums.types.MixedSeedsType;
import com.project.models.enums.types.SeedType;
import com.project.models.enums.types.TreeSourceType;

public class PlantSource extends Item {
    private SeedType seedType;
    private MixedSeedsType mixedSeedsType;
    private TreeSourceType treeSourceType;
    private boolean isMixedSeed;
    private boolean isTreeSeed;
    private String name;

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
        this.name = seedType.getName();
    }

    @Override
    public String getName() {
        return this.name;
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

