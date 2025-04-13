package models.farming;

import models.enums.types.FruitType;
import models.enums.types.Seed;

public class Tree extends Harvestable {
    private FruitType fruit;
    private int fruitHarvestCycle;
    private boolean isBurnt;
    private Seed seed;
}
