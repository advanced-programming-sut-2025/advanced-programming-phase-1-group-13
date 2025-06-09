package com.ap_project.models;

import com.ap_project.models.enums.types.ShopType;
import com.ap_project.models.enums.types.TileType;

import java.util.ArrayList;
import java.util.List;

public class NPCVillage {
    public static ArrayList<Shop> shops;
    private ArrayList<Tile> villageTiles;
    private final int width = 30;
    private final int height = 30;

    public NPCVillage() {
        this.shops = new ArrayList<>();
        this.villageTiles = new ArrayList<>();

        initializeVillageTiles();

        addShop(ShopType.BLACKSMITH, new Position(7, 5), 3, 3);
        addShop(ShopType.JOJAMART, new Position(2, 13), 3, 3);
        addShop(ShopType.PIERRE_GENERAL_STORE, new Position(10, 20), 5, 5);
        addShop(ShopType.CARPENTER_SHOP, new Position(3, 26), 2, 2);
        addShop(ShopType.FISH_SHOP, new Position(24, 8), 4, 4);
        addShop(ShopType.MARNIE_RANCH, new Position(19, 1), 3, 3);
        addShop(ShopType.THE_STARDROP_SALOON, new Position(25, 17), 2, 2);
    }

    private void initializeVillageTiles() {
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                villageTiles.add(new Tile(new Position(x, y), TileType.NOT_PLOWED_SOIL));
            }
        }
    }

    private void addShop(ShopType type, Position position, int width, int height) {
        Shop shop = new Shop(type, position, width, height);
        shops.add(shop);

        for (int x = position.getX(); x < position.getX() + width; x++) {
            for (int y = position.getY(); y < position.getY() + height; y++) {
                Position tilePos = new Position(x, y);
                Tile tile = getTileByPosition(tilePos);
                if (tile != null) {
                    tile.setType(TileType.SHOP);
                }
            }
        }
    }

    public static ArrayList<Shop> getShops() {
        return shops;
    }

    public Shop getShopAtPosition(Position position) {
        for (Shop shop : shops) {
            if (shop.containsPosition(position)) {
                return shop;
            }
        }
        return null;
    }

    public Shop getShopByType(ShopType type) {
        for (Shop shop : shops) {
            if (shop.getType() == type) {
                return shop;
            }
        }
        return null;
    }

    public boolean isPositionValid(Position pos) {
        return pos.getX() >= 0 && pos.getX() < width &&
                pos.getY() >= 0 && pos.getY() < height;
    }

    public Tile getTileByPosition(Position pos) {
        if (!isPositionValid(pos)) return null;
        for (Tile tile : villageTiles) {
            if (tile.getPosition().getY() == pos.getY() && tile.getPosition().getX() == pos.getX()) {
                return tile;
            }
        }
        return null;
    }

    public List<Tile> getVillageTiles() {
        return villageTiles;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
