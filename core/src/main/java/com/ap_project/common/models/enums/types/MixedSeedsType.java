package com.ap_project.common.models.enums.types;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.asList;

public enum MixedSeedsType implements ItemType {
    SPRING_MIXED_SEED(new ArrayList<>(asList(CropType.CAULIFLOWER, CropType.PARSNIP, CropType.POTATO,
            CropType.BLUE_JAZZ, CropType.TULIP))),
    SUMMER_MIXED_SEED(new ArrayList<>(asList(CropType.CORN, CropType.HOT_PEPPER, CropType.RADISH,
            CropType.WHEAT, CropType.POPPY, CropType.SUNFLOWER, CropType.SUMMER_SPANGLE))),
    FALL_MIXED_SEED(new ArrayList<>(asList(CropType.ARTICHOKE, CropType.CORN, CropType.EGGPLANT,
            CropType.PUMPKIN, CropType.SUNFLOWER, CropType.FAIRY_ROSE))),
    WINTER_MIXED_SEED(new ArrayList<>(List.of(CropType.POWDERMELON))),
    ;

    private final ArrayList<CropType> possibleCrops;

    MixedSeedsType(ArrayList<CropType> possibleCrops) {
        this.possibleCrops = possibleCrops;
    }

    public ArrayList<CropType> getPossibleCrops() {
        return possibleCrops;
    }

    @Override
    public String getName() {
        return "";
    }
}
