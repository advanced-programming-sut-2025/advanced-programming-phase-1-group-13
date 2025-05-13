package models;

import models.enums.environment.Time;
import models.enums.types.ShopType;
import models.enums.types.TileType;

import java.util.ArrayList;
import java.util.HashMap;

public class Shop {
    private final String name;
    private final ShopType type;
    private final ArrayList<Item> shopInventory;
    private int balance = 0;
    private final NPC owner;
    private final int startHour;
    private final int endHour;
    private ArrayList<Tile> shopTiles;
    private Position position;  // Add position field to track shop location

    public Shop(ShopType type, Position position, int width, int height) {
        this.type = type;
        this.shopInventory = new ArrayList<>();
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
                Tile tile = new Tile();
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
        for (Tile tile : shopTiles) {
            if (tile.getPosition().equals(position)) {
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

    public ArrayList<Item> getShopInventory() {
        return shopInventory;
    }

    public int getBalance() {
        return balance;
    }

    public NPC getOwner() {
        return owner;
    }

    public boolean isOpen() {
        int currentHour = App.getCurrentGame().getGameState().getTime().getHour();
        return currentHour >= startHour && currentHour <= endHour;
    }

    public void changeBalance(int amount) {
        this.balance += amount;
    }


    void addProduct(Item item, int count) {
        // TODO: Implement
    }

    void removeProduct(Item item, int count) {
        // TODO: Implement
    }

    void sellProduct(Item item, int count) {
        // TODO: Implement
    }

    void showAvailableProducts() {
        // TODO: Implement
    }
}