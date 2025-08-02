package com.ap_project.common.models.farming;

import com.ap_project.common.models.enums.environment.Season;
import com.ap_project.common.models.enums.types.SeedType;

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
