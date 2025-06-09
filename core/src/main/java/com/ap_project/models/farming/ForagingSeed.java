package com.project.models.farming;

import com.project.models.enums.environment.Season;
import com.project.models.enums.types.SeedType;

import java.util.ArrayList;

public class ForagingSeed extends PlantSource implements ForagingStuff {
    private ArrayList<Season> seasons;
    private SeedType seedType;
    private String name;

    public ForagingSeed(SeedType seedType) {
        super(seedType);
        this.name = seedType.getName();
    }

    @Override
    public void generate() {

    }

    @Override
    public String getName() {
        return this.name;
    }
}
