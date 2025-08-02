package com.ap_project.common.models.enums.types;

public enum FoodBuff {
    NOTHING("no buff!", 0),
    MAX_ENERGY_PLUS_100("Max Energy +100 (5 hours)", 5),
    MAX_ENERGY_PLUS_50("Max Energy +50 (3 hours)", 3),
    FARMING_5_HOURS("Farming (5 hours)", 5),
    FORAGING_11_HOURS("Foraging (11 hours)", 11),
    FORAGING_5_HOURS("Foraging (5 hours)", 5),
    FISHING_5_HOURS("Fishing (5 hours)", 5),
    FISHING_10_HOURS("Fishing (10 hours)", 10),
    MINING_5_HOURS("Mining (5 hours)", 5);

    private final String buffDisplayName;
    private final int buffDurationInHours;

    FoodBuff(String buffDisplayName, int buffDurationInHours) {
        this.buffDisplayName = buffDisplayName;
        this.buffDurationInHours = buffDurationInHours;
    }

    public String getBuffDisplayName() {
        return this.buffDisplayName;
    }

    public int getBuffDurationInHours() {
        return buffDurationInHours;
    }
}
