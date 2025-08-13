package com.ap_project.common.models;

import com.ap_project.client.controllers.game.NPCDialogGenerator;
import com.ap_project.common.models.enums.environment.Direction;
import com.ap_project.common.models.enums.environment.Season;
import com.ap_project.common.models.enums.environment.Weather;
import com.ap_project.common.models.enums.types.Dialog;
import com.ap_project.common.models.enums.types.ItemType;
import com.ap_project.common.models.enums.types.NPCType;
import com.ap_project.common.models.enums.types.Role;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

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
    private final Animation<Texture> upAnimation;
    private final Animation<Texture> downAnimation;
    private final Animation<Texture> leftAnimation;
    private final Animation<Texture> rightAnimation;
    private float stateTime;
    private float x;
    private float y;

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
        return Dialog.getDialogBySituation(type, timeOfDay, season, weather, friendshipLevel);
    }

    public String generateDialog() {
        int currentHour = App.getCurrentGame().getGameState().getTime().getHour();
        Season currentSeason = App.getCurrentGame().getGameState().getTime().getSeason();
        Weather currentWeather = App.getCurrentGame().getGameState().getCurrentWeather();
        List<String> previousDialogues = List.of(
            "Good morning!",
            "The crops look great this season."
        );
        try {
            System.out.println("GENERATING DIALOGUE...");
            String npcDialogue = NPCDialogGenerator.getDynamicDialogue(
                this.type, currentHour, currentSeason, currentWeather, previousDialogues
            );

            System.out.println("NPC says: " + npcDialogue);

            String[] lines = npcDialogue.split("\n");
            StringBuilder fullDialogue = new StringBuilder();

            Gson gson = new Gson();

            for (String line : lines) {
                if (line.trim().isEmpty()) continue;

                try {
                    JsonObject obj = gson.fromJson(line, JsonObject.class);
                    if (obj.has("response")) {
                        fullDialogue.append(obj.get("response").getAsString());
                    }
                    if (obj.has("done") && obj.get("done").getAsBoolean()) {
                        break;
                    }
                } catch (JsonSyntaxException e) {
                    System.err.println("Invalid JSON line: " + line);
                }
            }
            System.out.println(name + ": " + fullDialogue);
            return fullDialogue.toString();
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
            return getDialog().getMessage();
        }
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
                position = new Position(10, 10);
            }
        }
        this.position.setX(position.getX() - 2);

        x = position.getX();
        y = position.getY();
    }

    public Direction getDirection() {
        return direction;
    }

    public void moveRandomly() {
        Random random = new Random();
        if (random.nextFloat() > 0.01f && direction != null) {
            isWalking = true;
        } else {
            Direction[] directions = Direction.values();
            int currentOrdinal = direction != null ? direction.ordinal() : 0;
            float rand = random.nextFloat();
            if (rand < 0.5f) {
                int newOrdinal = currentOrdinal + (random.nextBoolean() ? 1 : -1);
                if (newOrdinal < 0) newOrdinal = directions.length - 1;
                if (newOrdinal >= directions.length) newOrdinal = 0;
                direction = directions[newOrdinal];
            } else {
                direction = directions[random.nextInt(directions.length)];
            }
            isWalking = true;
        }
    }

    public Texture getTexture() {
        Texture texture = downAnimation.getKeyFrame(stateTime);
        if (isWalking) {
            if (direction == Direction.UP) {
                y -= 0.1f;
                texture = upAnimation.getKeyFrame(stateTime);
            }

            if (direction == Direction.DOWN) {
                y += 0.1f;
                texture = downAnimation.getKeyFrame(stateTime);
            }

            if (direction == Direction.LEFT) {
                x -= 0.1f;
                texture = leftAnimation.getKeyFrame(stateTime);
            }

            if (direction == Direction.RIGHT) {
                x += 0.1f;
                texture = rightAnimation.getKeyFrame(stateTime);
            }

            if (x < 0) x = 0;
            if (x > 74) x = 74;
            if (y < 0) y = 0;
            if (y > 55) y = 55;

            position = new Position((int) x, (int) y);
            return texture;
        };

        return GameAssetManager.getGameAssetManager().getNPCIdle(type, direction);
    }

    public int getStartOfFreeTime() {
        if (App.getCurrentGame().getVillage().getShopByOwner(this) != null) {
            return App.getCurrentGame().getVillage().getShopByOwner(this).getType().getEndHour();
        }
        return 16;
    }

    public void changeStateTime(float delta) {
        this.stateTime += delta;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }
}
