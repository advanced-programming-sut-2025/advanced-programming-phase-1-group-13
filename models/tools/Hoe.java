package models;
import models.enums.types.ToolTypes;

import java.util.Map;

public class Hoe extends Tool {
    private ToolLevel level;

    private static final Map<ToolLevel, Integer> ENERGY_COSTS = Map.of(
            ToolLevel.BASIC, 5,
            ToolLevel.COPPER, 4,
            ToolLevel.IRON, 3,
            ToolLevel.GOLD, 2,
            ToolLevel.IRIDIUM, 1
    );

    public Hoe(ToolLevel level) {
        super(level.name() + " Hoe", ToolTypes.HOE, ENERGY_COSTS.get(level));
        this.level = level;
    }

    public void tillSoil() {
        System.out.println(getName() + " is tilling the soil.");
    }

    public ToolLevel getLevel() {
        return level;
    }
}

