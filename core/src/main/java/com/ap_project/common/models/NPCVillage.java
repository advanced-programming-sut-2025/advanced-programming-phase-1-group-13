package com.ap_project.common.models;

import com.ap_project.common.models.enums.types.ShopType;
import com.ap_project.common.models.enums.types.TileType;

import java.util.ArrayList;

public class NPCVillage {
    public static ArrayList<Shop> shops;
    private final ArrayList<Tile> villageTiles;
    private final int width = 74;
    private final int height = 55;

    public NPCVillage() {
        shops = new ArrayList<>();
        this.villageTiles = new ArrayList<>();

        initializeVillageTiles();

        addShop(ShopType.BLACKSMITH, new Position(10, 25), 5, 6);
        addShop(ShopType.JOJAMART, new Position(35, 8), 12, 10);
        addShop(ShopType.PIERRE_GENERAL_STORE, new Position(60, 25), 5, 5);
        addShop(ShopType.CARPENTER_SHOP, new Position(60, 8), 16, 9);
        addShop(ShopType.FISH_SHOP, new Position(30, 42), 9, 7);
        addShop(ShopType.MARNIE_RANCH, new Position(10, 35), 22, 10);
        addShop(ShopType.THE_STARDROP_SALOON, new Position(55, 41), 6, 7);
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

    public boolean isPositionInvalid(Position pos) {
        return pos.getX() < 0 || pos.getX() >= width ||
            pos.getY() < 0 || pos.getY() >= height;
    }

    public Tile getTileByPosition(Position pos) {
        if (isPositionInvalid(pos)) return null;
        for (Tile tile : villageTiles) {
            if (tile.getPosition().getY() == pos.getY() && tile.getPosition().getX() == pos.getX()) {
                return tile;
            }
        }
        return null;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
