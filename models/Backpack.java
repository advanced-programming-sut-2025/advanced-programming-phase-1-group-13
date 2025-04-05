package models;

import models.enums.types.BackpackLevel;
import models.enums.types.ToolTypes;

import java.util.Map;

public class Backpack extends Tool {
    private BackpackLevel level;

    private static final Map<BackpackLevel, Integer> CAPACITY_MAP = Map.of(
            BackpackLevel.INITIAL, 12,
            BackpackLevel.LARGE, 24,
            BackpackLevel.DELUXE, Integer.MAX_VALUE // Infinite space
    );

    public Backpack(BackpackLevel level) {
        super("Backpack", ToolTypes.BACKPACK, 0);
        this.level = level;
    }

    public int getCapacity() {
        return CAPACITY_MAP.get(level);
    }

    public BackpackLevel getBackpackLevel() {
        return level;
    }

    @Override
    public ToolTypes getType() {
        return ToolTypes.BACKPACK;
    }
}
