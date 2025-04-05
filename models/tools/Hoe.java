package models.tools;
import models.enums.types.ToolMaterial;
import models.enums.types.ToolTypes;

import java.util.Map;

public class Hoe extends Tool {
    private ToolMaterial level;

    private static final Map<ToolMaterial, Integer> ENERGY_COSTS = Map.of(
            ToolMaterial.BASIC, 5,
            ToolMaterial.COPPER, 4,
            ToolMaterial.IRON, 3,
            ToolMaterial.GOLD, 2,
            ToolMaterial.IRIDIUM, 1
    );

    public Hoe(ToolMaterial level) {
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

