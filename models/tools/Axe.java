package models.tools;
import models.enums.types.ToolTypes;

import java.util.Map;

public class Axe extends Tool {
    private ToolLevel level;

    private static final Map<ToolLevel, Integer> ENERGY_COSTS = Map.of(
            ToolLevel.BASIC, 5,
            ToolLevel.COPPER, 4,
            ToolLevel.IRON, 3,
            ToolLevel.GOLD, 2,
            ToolLevel.IRIDIUM, 1
    );

    public Axe(ToolLevel level) {
        super(level.name() + " Axe", ToolTypes.AXE, ENERGY_COSTS.get(level));
        this.level = level;
    }

    public void chopTree() {
        System.out.println(getName() + " is chopping a tree.");
    }

    public ToolLevel getLevel() {
        return level;
    }
}
