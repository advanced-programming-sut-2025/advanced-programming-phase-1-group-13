package models;
import models.enums.types.FishingRodType;
import models.enums.types.ToolTypes;

import java.util.Map;

public class FishingRod extends Tools {
    private FishingRodType type;

    private static final Map<FishingRodType, Integer> ENERGY_COSTS = Map.of(
            FishingRodType.TRAINING, 8,
            FishingRodType.BAMBOO, 8,
            FishingRodType.FIBERGLASS, 6,
            FishingRodType.IRIDIUM, 4
    );

    public FishingRod(FishingRodType type) {
        super(type.name() + " Fishing Rod", ToolTypes.FISHING_ROD, ENERGY_COSTS.get(type));
        this.type = type;
    }

    public void castLine() {
        System.out.println(getName() + " is casting a fishing line.");
    }

    public FishingRodType getRodType() {
        return type;
    }
}

