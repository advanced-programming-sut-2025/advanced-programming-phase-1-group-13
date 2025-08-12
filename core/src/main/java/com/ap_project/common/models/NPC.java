package com.ap_project.common.models;

import com.ap_project.common.models.enums.environment.Direction;
import com.ap_project.common.models.enums.environment.Season;
import com.ap_project.common.models.enums.environment.Weather;
import com.ap_project.common.models.enums.types.Dialog;
import com.ap_project.common.models.enums.types.ItemType;
import com.ap_project.common.models.enums.types.NPCType;
import com.ap_project.common.models.enums.types.Role;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;

import java.util.ArrayList;
import java.util.HashMap;

import static com.ap_project.common.models.Item.getItemTypeByItemName;

public class NPC {
    private final NPCType type;
    private final String name;
    private final Role role;
    private final ArrayList<ItemType> favorites;
    private ArrayList<Dialog> dialogs;
    private Position position;
    private final HashMap<User, Boolean> giftReceivedToday;
    private final HashMap<User, Boolean> talkedToToday;
    private HashMap<User, Integer> daysLeftToUnlockThirdQuest;
    private Direction direction;
    private boolean isWalking;
    private Animation<Texture> upAnimation;
    private Animation<Texture> downAnimation;
    private Animation<Texture> leftAnimation;
    private Animation<Texture> rightAnimation;

    public NPC(NPCType type, Game game) {
        this.type = type;
        this.name = type.getName();
        this.role = type.getRole();
        resetPosition(game);
        this.direction = Direction.DOWN;

        this.favorites = type.getFavorites();
        this.giftReceivedToday = new HashMap<>();
        this.talkedToToday = new HashMap<>();
        this.daysLeftToUnlockThirdQuest = new HashMap<>();

        this.upAnimation = GameAssetManager.getGameAssetManager().getNPCAnimation(type, Direction.UP);
        this.downAnimation = GameAssetManager.getGameAssetManager().getNPCAnimation(type, Direction.DOWN);
        this.leftAnimation = GameAssetManager.getGameAssetManager().getNPCAnimation(type, Direction.LEFT);
        this.rightAnimation = GameAssetManager.getGameAssetManager().getNPCAnimation(type, Direction.RIGHT);
    }

    public NPCType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Role getRole() {
        return role;
    }

    public ArrayList<ItemType> getFavorites() {
        return favorites;
    }

    public ArrayList<Dialog> getDialogs() {
        return dialogs;
    }

    public Position getPosition() {
        return position;
    }

    public HashMap<User, Boolean> getGiftReceivedToday() {
        return giftReceivedToday;
    }

    public boolean hasReceivedGiftToday(User user) {
        return this.getGiftReceivedToday().get(user);
    }

    public void setReceivedGiftToday(User user, boolean hasReceived) {
        this.getGiftReceivedToday().put(user, hasReceived);
    }

    public HashMap<User, Boolean> getTalkedToToday() {
        return talkedToToday;
    }

    public boolean hasTalkedToToday(User user) {
        return this.getTalkedToToday().get(user);
    }

    public void setTalkedToToday(User user, boolean hasReceived) {
        this.getTalkedToToday().put(user, hasReceived);
    }

    public boolean isFavourite(String itemName) {
        return this.getType().getFavorites().contains(getItemTypeByItemName(itemName));
    }

    public Dialog getDialog() {
        Game game = App.getCurrentGame();
        int timeOfDay = game.getGameState().getTime().getHour();
        Season season = game.getGameState().getTime().getSeason();
        Weather weather = game.getGameState().getCurrentWeather();
        int friendshipLevel = game.getNpcFriendshipPoints(App.getLoggedIn(), this);
        Dialog dialog = Dialog.getDialogBySituation(type, timeOfDay, season, weather, friendshipLevel);
        return dialog;
    }

    public boolean hasDialog() {
        return this.getDialog() != null;
    }

    public boolean isWalking() {
        return isWalking;
    }

    public void resetPosition(Game game) {
        if (type.getHouse() != null) {
            this.position = new Position(type.getHouse());
        }
        else {
            try {
                position = game.getVillage().getShopByOwner(this).getPosition();
            } catch (Exception e) {
                System.out.println(name + " doesn't have a shop" + e.getMessage());
                e.printStackTrace();
                position = new Position(10, 10);
            }
        }
        this.position.setX(position.getX() - 2);

    }

    public Direction getDirection() {
        return direction;
    }

    public void changeToRandomDirection() {
        direction = Direction.values()[(int) (Math.random() * Direction.values().length)];
    }

    public int getStartOfFreeTime() {
        if (App.getCurrentGame().getVillage().getShopByOwner(this) != null) {
            return App.getCurrentGame().getVillage().getShopByOwner(this).getType().getEndHour();
        }
        return 16;
    }
}
