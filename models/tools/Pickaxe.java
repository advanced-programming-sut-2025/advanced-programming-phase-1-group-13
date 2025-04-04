package models;
import models.enums.types.ToolTypes;

import java.util.Map;

public class Pickaxe extends Tools {
    private ToolLevel level;

    private static final Map<ToolLevel, Integer> ENERGY_COSTS = Map.of(
            ToolLevel.BASIC, 5,
            ToolLevel.COPPER, 4,
            ToolLevel.IRON, 3,
            ToolLevel.GOLD, 2,
            ToolLevel.IRIDIUM, 1
    );

    public Pickaxe(ToolLevel level) {
        super(level.name() + " Pickaxe", ToolTypes.PICKAXE, ENERGY_COSTS.get(level));
        this.level = level;
    }

    public void mineRock() {
        System.out.println(getName() + " is breaking a rock.");
    }

    public ToolLevel getLevel() {
        return level;
    }
}
