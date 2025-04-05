package models;
import models.enums.types.ToolTypes;

import java.util.Map;

public class WateringCan extends Tool {
    private ToolLevel level;

    private static final Map<ToolLevel, Integer> ENERGY_COSTS = Map.of(
            ToolLevel.BASIC, 5,
            ToolLevel.COPPER, 4,
            ToolLevel.IRON, 3,
            ToolLevel.GOLD, 2,
            ToolLevel.IRIDIUM, 1
    );

    public WateringCan(ToolLevel level) {
        super(level.name() + " Watering Can", ToolTypes.WATERING_CAN, ENERGY_COSTS.get(level));
        this.level = level;
    }

    public void waterPlants() {
        System.out.println(getName() + " is watering crops.");
    }

    public ToolLevel getLevel() {
        return level;
    }
}


