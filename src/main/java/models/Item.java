package models;

import models.enums.types.*;

public abstract class Item {
    private boolean isSellable;
    private boolean isPurchasable;
    private int price;
    private String name;

    public String getName() {
        return this.name;
    }

    public boolean isSellable() {
        return this.isSellable;
    }

    public void setSellable(boolean sellable) {
        isSellable = sellable;
    }

    public boolean isPurchasable() {
        return this.isPurchasable;
    }

    public void setPurchasable(boolean purchasable) {
        isPurchasable = purchasable;
    }

    public int getPrice() {
        return this.price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public static ItemType getItemTypeByItemName(String itemName) {
        AnimalProductType animalProductType = AnimalProductType.getAnimalProductTypeByName(itemName);
        if (animalProductType != null) {
            return animalProductType;
        }

        CraftType craftType = CraftType.getCraftByName(itemName);
        if (craftType != null) {
            return craftType;
        }

        CropType cropType = CropType.getCropTypeByName(itemName);
        if (cropType != null) {
            return cropType;
        }

        FishType fishType = FishType.getFishTypeByName(itemName);
        if (fishType != null) {
            return fishType;
        }

        FoodType foodType = FoodType.getFoodTypeByName(itemName);
        if (foodType != null) {
            return foodType;
        }

        GoodsType goodsType = GoodsType.getGoodsTypeByName(itemName);
        if (goodsType != null) {
            return goodsType;
        }

        IngredientType ingredientType = IngredientType.getIngredientTypeByName(itemName);
        if (ingredientType != null) {
            return ingredientType;
        }

        MaterialType materialType = MaterialType.getMaterialTypeByName(itemName);
        if (materialType != null) {
            return materialType;
        }

        MineralType mineralType = MineralType.getMineralTypeByName(itemName);
        if (mineralType != null) {
            return mineralType;
        }

        ProcessedItemType processedItemType = ProcessedItemType.getProcessedItemTypeByName(itemName);
        if (processedItemType != null) {
            return processedItemType;
        }

        return ToolType.getToolTypeByName(itemName);
    }

    public static Item getItemByItemType(ItemType itemType) {
        // TODO
        return null;
    }

    public static Item getItemByItemName(String itemName) {
        ItemType itemType = getItemTypeByItemName(itemName);
        return getItemByItemType(itemType);
    }

    @Override
    public String toString() {
        return this.name;
    }
}
