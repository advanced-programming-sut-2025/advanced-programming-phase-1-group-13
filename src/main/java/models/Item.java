package models;

import models.enums.Quality;
import models.enums.types.*;
import models.farming.Crop;
import models.tools.Tool;

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
        if (itemType instanceof AnimalProductType) {
            return new AnimalProduct((AnimalProductType) itemType, Quality.NORMAL,
                    new Animal("", ((AnimalProductType) itemType).getAnimal(), null));
        }


        if (itemType instanceof CraftType) {
            return new Craft((CraftType) itemType);
        }

        if (itemType instanceof CropType) {
            return new Crop((CropType) itemType);
        }

        if (itemType instanceof FishType) {
            return new Fish((FishType) itemType, Quality.NORMAL);
        }

        if (itemType instanceof FoodType) {
            return new Food((FoodType) itemType);
        }

        if (itemType instanceof GoodsType) {
            return new Good((GoodsType) itemType);
        }

        if (itemType instanceof MaterialType) {
            return new Material((MaterialType) itemType);
        }

        if (itemType instanceof MineralType) {
            return new Mineral(null);
        }

        if (itemType instanceof ProcessedItemType) {
            return new ProcessedItem((ProcessedItemType) itemType);
        }

        return null;
    }

    public static Item getItemByItemName(String itemName) {
        ItemType itemType = getItemTypeByItemName(itemName);
        return getItemByItemType(itemType);
    }

    public static Quality getItemQuality(Item item) {
        if (item instanceof Fish) {
            return ((Fish) item).getQuality();
        }

        if (item instanceof AnimalProduct) {
            return ((AnimalProduct) item).getQuality();
        }

        return null;
    }

    public int getSellPrice() {
        Quality quality = Item.getItemQuality(this);
        if (quality == null) {
            return this.getPrice();
        }

        return  (int) (quality.getPriceCoefficient() * quality.getPriceCoefficient());
    }

    @Override
    public String toString() {
        return this.name;
    }
}
