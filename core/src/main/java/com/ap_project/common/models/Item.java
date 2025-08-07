package com.ap_project.common.models;

import com.ap_project.common.models.enums.Quality;
import com.ap_project.common.models.enums.types.*;
import com.ap_project.common.models.farming.Crop;
import com.ap_project.common.models.farming.ForagingCrop;
import com.ap_project.common.models.farming.PlantSource;
import com.ap_project.common.models.farming.Tree;
import com.ap_project.common.models.tools.*;

import java.util.Objects;

public abstract class Item {
    private boolean isSellable;
    private boolean isPurchasable;
    private int price;
    protected String name;

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
        SeedType seedType = SeedType.getSeedByName(itemName);
        if (seedType != null) {
            return seedType;
        }

        TreeSourceType treeSourceType = TreeSourceType.getTreeSourceTypeByName(itemName);
        if (treeSourceType != null) {
            return treeSourceType;
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
            AnimalProductType animalProductType = (AnimalProductType) itemType;
            return new AnimalProduct(animalProductType, Quality.NORMAL, null);
        }

        if (itemType instanceof CraftType) {
            return new Craft((CraftType) itemType);
        }
        if (itemType instanceof ToolType) {
            ToolType toolType = (ToolType) itemType;
            Tool result;
            switch (toolType) {
                case HOE:
                    result = new Hoe();
                    break;
                case PICKAXE:
                    result = new Pickaxe();
                    break;
                case AXE:
                    result = new Axe();
                    break;
                case WATERING_CAN:
                    result = new WateringCan();
                    break;
                case SCYTHE:
                    result = new Scythe();
                    break;
                case SHEARS:
                    result = new Shear();
                    break;
                case MILK_PAIL:
                    result = new MilkPail();
                    break;
                case TRASH_CAN:
                    result = new TrashCan();
                    break;
                case FISHING_ROD:
                    result = new FishingRod(null); // TODO
                    break;
                default:
                    result = null;
            }
            return result;
        }

        if (itemType instanceof CropType) {
            return new Crop((CropType) itemType);
        }

        if (itemType instanceof SeedType) {
            SeedType seedType = (SeedType) itemType;
            return new PlantSource(seedType);
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
            return new Mineral((MineralType) itemType);
        }

        if (itemType instanceof ProcessedItemType) {
            return new ProcessedItem((ProcessedItemType) itemType);
        }

        if (itemType instanceof ForagingCropType) {
            return new ForagingCrop((ForagingCropType) itemType);
        }
        if (itemType instanceof FruitType) {
            return new Fruit((FruitType) itemType);
        }

        if (itemType instanceof MixedSeedsType || itemType instanceof TreeSourceType) {
            return new PlantSource(itemType);
        }

        if (itemType instanceof TreeType) {
            return new Tree((TreeType) itemType);
        }
        if (itemType instanceof IngredientType) {
            return new Ingredient((IngredientType) itemType);
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

        return (int) (quality.getPriceCoefficient() * quality.getPriceCoefficient());
    }

    @Override
    public String toString() {
        return this.name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Item item = (Item) o;
        return Objects.equals(name, item.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name);
    }

}

