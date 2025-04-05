package models.enums.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ArtisanType {
//    KEG(createItemsList(ItemType.BEER, ItemType.VINEGAR, ItemType.JUICE, ItemType.PALE_ALE, ItemType.WINE)),
    CHEESE_PRESS(createItemsList(ProcessedItemType.CHEESE, ProcessedItemType.LARGE_CHEESE, ProcessedItemType.GOAT_CHEESE, ProcessedItemType.LARGE_GOAT_CHEESE)),
    LOOM(createItemsList(ProcessedItemType.CLOTH)),
//    MAYO_MACHINE(createItemsList(ItemType.MAYONNAISE, ItemType.LARGE_MAYONNAISE, ItemType.DUCK_MAYONNAISE, ItemType.DINOSAUR_MAYONNAISE)),
//    OIL_MAKER(createItemsList(ItemType.TRUFFLE_OIL, ItemType.OIL)),
//    PRESERVES_JAR(createItemsList(ItemType.PICKLES, ItemType.JELLY)),
//    DRYING_RACK(createItemsList(ItemType.DRIED_MUSHROOMS, ItemType.DRIED_FRUIT, ItemType.RAISINS)),
//    SMOKER(createItemsList(ItemType.SMOKED_FISH)),
//    RECYCLER(createItemsList(ItemType.COAL, ItemType.METAL_BAR));
    ;

    private final List<ProcessedItemType> items;

    ArtisanType(List<ProcessedItemType> items) {
        this.items = items;
    }

    public List<ProcessedItemType> getItems() {
        return items;
    }

    private static List<ProcessedItemType> createItemsList(ProcessedItemType... items) {
        return new ArrayList<>(Arrays.asList(items));
    }
}
