package com.ap_project.common.models;

import com.ap_project.common.models.enums.types.CropType;
import com.ap_project.common.models.enums.types.GoodsType;
import com.ap_project.common.models.enums.types.TileType;
import com.ap_project.common.models.farming.Crop;
import com.ap_project.common.models.farming.PlantSource;
import com.ap_project.common.models.farming.Tree;

public class Tile {
    private TileType type;
    private Position position;
    private Item itemPLacedOnTile;

    public TileType getType() {
        return this.type;
    }

    public Tile(Position position) {
        this.position = position;
    }

    public Tile(Position position, TileType type) {
        this.position = position;
        this.type = type;
    }

    public void setType(TileType type) {
        this.type = type;
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void pLaceItemOnTile(Item itemPLacedOnTile) {
        this.itemPLacedOnTile = itemPLacedOnTile;
        if (itemPLacedOnTile instanceof Tree) this.setType(TileType.TREE);
        else if (itemPLacedOnTile instanceof PlantSource) this.setType(TileType.PLANTED_SEED);
        else if (itemPLacedOnTile instanceof Crop) this.setType(TileType.GROWING_CROP);
        else if (itemPLacedOnTile instanceof Good && ((Good) itemPLacedOnTile).getType() == GoodsType.WOOD) {
            this.setType(TileType.WOOD_LOG);
        } else this.setType(TileType.UNDER_AN_ITEM);
    }

    public Item getItemPlacedOnTile() {
        return itemPLacedOnTile;
    }

}
