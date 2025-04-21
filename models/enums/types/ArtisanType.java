package models.enums.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ArtisanType {
    KEG(createItemsList(ProcessedItemType.BEER, ProcessedItemType.VINEGAR, ProcessedItemType.JUICE,
            ProcessedItemType.PALE_ALE, ProcessedItemType.WINE)),
    CHEESE_PRESS(createItemsList(ProcessedItemType.CHEESE, ProcessedItemType.LARGE_CHEESE,
            ProcessedItemType.GOAT_CHEESE, ProcessedItemType.LARGE_GOAT_CHEESE)),
    LOOM(createItemsList(ProcessedItemType.CLOTH)),
    MAYO_MACHINE(createItemsList(ProcessedItemType.MAYONNAISE, ProcessedItemType.LARGE_MAYONNAISE,
            ProcessedItemType.DUCK_MAYONNAISE, ProcessedItemType.DINOSAUR_MAYONNAISE)),
    OIL_MAKER(createItemsList(ProcessedItemType.TRUFFLE_OIL, ProcessedItemType.OIL)),
    PRESERVES_JAR(createItemsList(ProcessedItemType.PICKLES, ProcessedItemType.JELLY)),
    DRYING_RACK(createItemsList(ProcessedItemType.DRIED_MUSHROOMS, ProcessedItemType.DRIED_FRUIT, ProcessedItemType.RAISINS)),
    SMOKER(createItemsList(ProcessedItemType.SMOKED_FISH)),
    RECYCLER(createItemsList(ProcessedItemType.COAL, ProcessedItemType.METAL_BAR));

    private final List<ProcessedItemType> itemsTheArtisanProduces;

    ArtisanType(List<ProcessedItemType> items) {
        this.itemsTheArtisanProduces = items;
    }

    public List<ProcessedItemType> getItemsTheArtisanProduces() {
        return itemsTheArtisanProduces;
    }

    private static List<ProcessedItemType> createItemsList(ProcessedItemType... items) {
        return new ArrayList<>(Arrays.asList(items));
    }
}