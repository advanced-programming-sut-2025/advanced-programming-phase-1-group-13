package models.farming;

import models.enums.environment.Season;
import models.enums.types.SeedType;

import java.util.ArrayList;

public class ForagingSeed extends PlantSource implements ForagingStuff {
    private ArrayList<Season> seasons;

    public ForagingSeed(SeedType seedType) {
        super(seedType);
    }

    @Override
    public void generate() {

    }
}
