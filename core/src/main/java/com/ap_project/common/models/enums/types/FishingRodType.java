package com.ap_project.common.models.enums.types;

import com.ap_project.common.models.enums.SkillLevel;

public enum FishingRodType {
    TRAINING("Training", 8, 0.1, 25, SkillLevel.LEVEL_ONE),
    BAMBOO("Bamboo", 8, 0.5, 25, SkillLevel.LEVEL_ONE),
    FIBERGLASS("Fiberglass", 6, 0.9, 25, SkillLevel.LEVEL_TWO),
    IRIDIUM("Iridium", 4, 1.2, 25, SkillLevel.LEVEL_FOUR);

    private final String name;
    private final int energy;
    private final double qualityNumber;
    private final int buyingPrice;
    private final SkillLevel skillRequiredToBuy;

    FishingRodType(String name, int energy, double qualityNumber, int buyingPrice, SkillLevel skillRequiredToBuy) {
        this.name = name;
        this.energy = energy;
        this.qualityNumber = qualityNumber;
        this.buyingPrice = buyingPrice;
        this.skillRequiredToBuy = skillRequiredToBuy;
    }

    public String getName() {
        return this.name;
    }

    public int getEnergy() {
        return this.energy;
    }

    public double getQualityNumber() {
        return this.qualityNumber;
    }

    public int getBuyingPrice() {
        return this.buyingPrice;
    }

    public SkillLevel getSkillRequiredToBuy() {
        return this.skillRequiredToBuy;
    }

    public FishingRodType getFishingRodTypeByName(String name) {
        for (FishingRodType type : FishingRodType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }
}

