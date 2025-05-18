package models;

import models.enums.types.GoodsType;
import models.enums.types.ShopType;
import models.enums.types.TileType;

import java.util.ArrayList;

public class Shop {
    private final String name;
    private final ShopType type;
    private final ArrayList<Good> shopInventory;
    private final NPC owner;
    private final int startHour;
    private final int endHour;
    private final ArrayList<Tile> shopTiles;
    private final Position position;  // Add position field to track shop location

    public Shop(ShopType type, Position position, int width, int height) {
        this.type = type;
        this.shopInventory = new ArrayList<>();
        for (GoodsType goodType : GoodsType.values()) {
            this.shopInventory.add(new Good(goodType));
        }
        this.name = type.getName();
        this.owner = type.getOwner();
        this.startHour = type.getStartHour();
        this.endHour = type.getEndHour();
        this.position = position;
        this.shopTiles = new ArrayList<>();

        initializeShopTiles(width, height);
    }

    private void initializeShopTiles(int width, int height) {
        for (int x = position.getX(); x < position.getX() + width; x++) {
            for (int y = position.getY(); y < position.getY() + height; y++) {
                Tile tile = new Tile(position, TileType.SHOP);
                tile.setPosition(new Position(x, y));
                tile.setType(TileType.SHOP);
                shopTiles.add(tile);
            }
        }
    }

    public ArrayList<Tile> getShopTiles() {
        return this.shopTiles;
    }

    public Position getPosition() {
        return position;
    }

    public boolean containsPosition(Position position) {
        for (Tile tile : this.shopTiles) {
            System.out.println(tile.getPosition());
            if (tile.getPosition().getX() == position.getX() && tile.getPosition().getY() == position.getY()) {
                return true;
            }
        }
        return false;
    }

    public String getName() {
        return name;
    }

    public ShopType getType() {
        return type;
    }

    public ArrayList<Good> getShopInventory() {
        return shopInventory;
    }

    public Good getGoodByType(GoodsType type) {
        for (Good good : shopInventory) {
            if (good.getType().equals(type)) {
                return good;
            }
        }
        return null;
    }

    public NPC getOwner() {
        return owner;
    }

    public boolean isOpen() {
        int currentHour = App.getCurrentGame().getGameState().getTime().getHour();
        return currentHour >= startHour && currentHour <= endHour;
    }
}