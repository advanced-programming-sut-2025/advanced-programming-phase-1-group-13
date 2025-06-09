package com.ap_project.models.enums.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ArtisanType {
    KEG("Keg", new ArrayList<>(Arrays.asList(ProcessedItemType.BEER, ProcessedItemType.VINEGAR, ProcessedItemType.JUICE,
            ProcessedItemType.PALE_ALE, ProcessedItemType.WINE))),
    CHEESE_PRESS("Cheese Press", new ArrayList<>(Arrays.asList(ProcessedItemType.CHEESE,
            ProcessedItemType.LARGE_CHEESE,
            ProcessedItemType.GOAT_CHEESE, ProcessedItemType.LARGE_GOAT_CHEESE))),
    LOOM("Loom", new ArrayList<>(List.of(ProcessedItemType.CLOTH))),
    MAYO_MACHINE("Mayo Machine", new ArrayList<>(Arrays.asList(ProcessedItemType.MAYONNAISE,
            ProcessedItemType.LARGE_MAYONNAISE,
            ProcessedItemType.DUCK_MAYONNAISE, ProcessedItemType.DINOSAUR_MAYONNAISE))),
    OIL_MAKER("Oil Maker", new ArrayList<>(Arrays.asList(ProcessedItemType.TRUFFLE_OIL, ProcessedItemType.OIL))),
    PRESERVES_JAR("Preserves Jar", new ArrayList<>(Arrays.asList(ProcessedItemType.PICKLES,
            ProcessedItemType.JELLY))),
    DRYING_RACK("Drying Rack", new ArrayList<>(Arrays.asList(ProcessedItemType.DRIED_MUSHROOMS,
            ProcessedItemType.DRIED_FRUIT, ProcessedItemType.RAISINS))),
    SMOKER("Smoker", new ArrayList<>(List.of(ProcessedItemType.SMOKED_FISH))),
    RECYCLER("Recycler", new ArrayList<>(Arrays.asList(ProcessedItemType.COAL, ProcessedItemType.METAL_BAR)));

    private final String name;
    private final List<ProcessedItemType> itemsTheArtisanProduces;

    ArtisanType(String name, List<ProcessedItemType> itemsTheArtisanProduces) {
        this.name = name;
        this.itemsTheArtisanProduces = itemsTheArtisanProduces;
    }

    public String getName() {
        return name;
    }

    public static ArtisanType getArtisanTypeByArtisanName(String artisanNameString) {
        for (ArtisanType artisanType : ArtisanType.values()) {
            if (artisanType.getName().equals(artisanNameString)) {
                return artisanType;
            }
        }
        return null;
    }

    public List<ProcessedItemType> getItemsTheArtisanProduces() {
        return itemsTheArtisanProduces;
    }
}
