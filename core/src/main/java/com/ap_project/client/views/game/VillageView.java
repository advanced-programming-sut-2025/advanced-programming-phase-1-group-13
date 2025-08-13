package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.game.GameController;
import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.types.GameMenuType;
import com.ap_project.common.models.enums.types.GoodsType;
import com.ap_project.common.models.enums.types.NPCType;
import com.ap_project.common.models.network.Message;
import com.ap_project.common.models.network.MessageType;
import com.ap_project.common.utils.JSONUtils;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;
import java.util.HashMap;

import static com.ap_project.Main.*;

public class VillageView extends GameView {
    private final ArrayList<Texture> shopTextures;
    private ArrayList<Texture> npcTextures;
    private final ArrayList<Texture> npcHousesTextures;
    private final ArrayList<Texture> otherPlayersTextures;
    private NPC npcWithMenu;
    private Texture npcOptions;
    private NPC npcWithGift;
    private User playerWithFlower;
    public User playerWithGift;
    private final Texture giveGiftButton;
    private Vector2 giveGiftPosition;
    private final Texture questsListButton;
    private Vector2 questsListPosition;
    private final Texture friendshipLevelButton;
    private Vector2 friendshipLevelPosition;
    private final ArrayList<Position> housePositions;

    private User playerWithMenu;
    private Texture playerOptions;
    private final Texture hugButton;
    private Vector2 hugPosition;
    private final Texture proposeButton;
    private Vector2 proposePosition;
    private final Texture giveFlowerButton;
    private Vector2 giveFlowerPosition;


    public VillageView(GameController controller, Skin skin) {
        super(controller, skin);

        this.background = GameAssetManager.getGameAssetManager().getVillage(App.getCurrentGame().getGameState().getTime().getSeason());

        this.shopTextures = new ArrayList<>();
        for (Shop shop : NPCVillage.getShops()) {
            shopTextures.add(GameAssetManager.getGameAssetManager().getShop(shop.getType(), App.getCurrentGame().getGameState().getTime().getSeason()));
        }

        this.housePositions = new ArrayList<>();
        this.npcHousesTextures = new ArrayList<>();
        this.npcTextures = new ArrayList<>();
        for (NPCType npcType : NPCType.values()) {
            npcTextures.add(GameAssetManager.getGameAssetManager().getNPCIdle(npcType, App.getCurrentGame().getNpcs().get(npcType.ordinal()).getDirection()));
            if (npcType.getHouse() != null) {
                npcHousesTextures.add(GameAssetManager.getGameAssetManager().getNPCHouse(npcType));
                housePositions.add(npcType.getHouse());
            }
        }

        this.otherPlayersTextures = new ArrayList<>();
        for (User player : App.getCurrentGame().getPlayers()) {
            if (player.equals(App.getLoggedIn())) continue;
            otherPlayersTextures.add(GameAssetManager.getGameAssetManager().getIdlePlayer(
                player.getGender(),
                player.getDirection())
            );
        }

        this.npcOptions = null;

        this.npcWithGift = null;

        this.playerWithFlower = null;

        this.friendshipLevelButton = GameAssetManager.getGameAssetManager().getFriendshipLevelButton();
        this.giveGiftButton = GameAssetManager.getGameAssetManager().getGiveGiftButton();
        this.questsListButton = GameAssetManager.getGameAssetManager().getQuestsListButton();

        this.hugButton = GameAssetManager.getGameAssetManager().getHugButton();
        this.proposeButton = GameAssetManager.getGameAssetManager().getProposeButton();
        this.giveFlowerButton = GameAssetManager.getGameAssetManager().getGiveFlowerButton();
    }

    @Override
    public void renderMap(float delta) {
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();

        this.npcTextures = new ArrayList<>();
        for (int i = 0; i < App.getCurrentGame().getNpcs().size(); i++) {
            NPC npc = App.getCurrentGame().getNpcs().get(i);
            if (App.getCurrentGame().getGameState().getTime().getHour() >= npc.getStartOfFreeTime()) {
                npc.moveRandomly();
            }
            npc.changeStateTime(delta);
            npcTextures.add(npc.getTexture());
        }

        scale = 4.400316f;
        for (int i = 0; i < shopTextures.size(); i++) {
            draw(shopTextures.get(i), NPCVillage.getShops().get(i).getPosition());
        }

        for (int i = 0; i < npcHousesTextures.size(); i++) {
            draw(npcHousesTextures.get(i), housePositions.get(i));
        }

        scale = 4;
        for (int i = 0; i < npcTextures.size(); i++) {
            NPC npc = App.getCurrentGame().getNpcs().get(i);

            draw(npcTextures.get(i), npc.getPosition());

            float temp = scale;
            if (npc.hasDialog()) {
                Texture dialogIcon = GameAssetManager.getGameAssetManager().getDialogIcon();
                scale = 1f;
                draw(dialogIcon, new Position(npc.getPosition().getX(), npc.getPosition().getY() - 2));
                scale = temp;
            }

            if (npc == npcWithGift) {
                Texture gift = GameAssetManager.getGameAssetManager().getGift();
                scale = 0.5f;
                draw(gift, npc.getPosition());
                scale = temp;
            }
        }

        scale = 1;
        for (int i = 0; i < otherPlayersTextures.size(); i++) {
            User player = App.getCurrentGame().getPlayers().get(i);
            if (player.equals(App.getLoggedIn())) continue;

            if (player.isInVillage()) {
                draw(otherPlayersTextures.get(i), player.getPosition());
            }

            float temp = scale;
            if (player == playerWithFlower) {
                Texture flower = GameAssetManager.getGameAssetManager().getFlower();
                scale = 1.5f;
                Position position = new Position(player.getPosition());
                position.setX(position.getX() + 1);
                draw(flower, position);
                scale = temp;
            }
        }

        if (npcOptions != null) {
            scale = 1f;
            Position position = new Position(npcWithMenu.getPosition());
            position.setY(position.getY() + 3);
            position.setX(position.getX() + 1);
            draw(npcOptions, position);

            giveGiftPosition = new Vector2(TILE_SIZE * (position.getX() + 0.35f), TILE_SIZE * (position.getY() - 2.4f));
            questsListPosition = new Vector2(TILE_SIZE * (position.getX() + 0.35f), TILE_SIZE * (position.getY() - 1.4f));
            friendshipLevelPosition = new Vector2(TILE_SIZE * (position.getX() + 0.35f), TILE_SIZE * (position.getY() - 0.4f));

            draw(giveGiftButton, giveGiftPosition);
            draw(questsListButton, questsListPosition);
            draw(friendshipLevelButton, friendshipLevelPosition);
        }

        if (playerOptions != null) {
            scale = 1f;
            Position position = new Position(playerWithMenu.getPosition());
            position.setY(position.getY() + 3);
            position.setX(position.getX() + 1);
            draw(playerOptions, position);

            hugPosition= new Vector2(TILE_SIZE * (position.getX() + 0.35f), TILE_SIZE * (position.getY() - 2.4f));
            proposePosition = new Vector2(TILE_SIZE * (position.getX() + 0.35f), TILE_SIZE * (position.getY() - 1.4f));
            giveFlowerPosition = new Vector2(TILE_SIZE * (position.getX() + 0.35f), TILE_SIZE * (position.getY() - 0.4f));

            draw(hugButton, hugPosition);
            draw(proposeButton, proposePosition);
            draw(giveFlowerButton, giveFlowerPosition);
        }

        scale = 4.400316f;

        Main.getBatch().end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        try {
            super.touchDown(screenX, screenY, pointer, button);

            for (int i = 0; i < npcTextures.size(); i++) {
                NPC npc = App.getCurrentGame().getNpcs().get(i);
                Position position = new Position(npc.getPosition());
                position.setY(position.getY() - 2);
                if (clickedOnTexture(screenX, screenY, GameAssetManager.getGameAssetManager().getDialogIcon(), position, 1)) {
                    if (npc.hasDialog()) {
                        addDialogBox(npc);
                        if (!npc.hasTalkedToToday(App.getLoggedIn())) {
                            App.getCurrentGame().changeFriendship(App.getLoggedIn(), npc, 20);
                        }
                        npc.setTalkedToToday(App.getLoggedIn(), true);
                    }
                    return true;
                }
            }

            for (int i = 0; i < shopTextures.size(); i++) {
                Shop shop = NPCVillage.getShops().get(i);
                if (clickedOnTexture(screenX, screenY, shopTextures.get(i), shop.getPosition(), 4.400316f)) {
                    if (shop.isOpen()) goToShopMenu(this, shop);
                    else errorMessageLabel.setText(shop.getName() + " Is closed.");
                    return true;
                }
            }

            if (button == Input.Buttons.RIGHT) {
                for (int i = 0; i < npcTextures.size(); i++) {
                    NPC npc = App.getCurrentGame().getNpcs().get(i);
                    if (clickedOnTexture(screenX, screenY, npcTextures.get(i), npc.getPosition(), scale)) {
                        npcOptions = GameAssetManager.getGameAssetManager().getThreeOptions();
                        npcWithMenu = npc;
                        return true;
                    }
                }
            }
            if (button == Input.Buttons.RIGHT) {
                for (int i = 0; i < otherPlayersTextures.size(); i++) {
                    User user = App.getCurrentGame().getPlayers().get(i);
                    if (user.equals(App.getLoggedIn())) continue;
                    if (clickedOnTexture(screenX, screenY, otherPlayersTextures.get(i), App.getCurrentGame().getPlayers().get(i).getPosition(), scale)) {
                        playerOptions = GameAssetManager.getGameAssetManager().getThreeOptions();
                        playerWithMenu = user;
                        return true;
                    }
                }
            }

            if (npcOptions != null) {
                if (clickedOnTexture(screenX, screenY, giveGiftButton, giveGiftPosition)) {
                    goToGiftNpcMenu(this, npcWithMenu);
                    return true;
                }

                if (clickedOnTexture(screenX, screenY, friendshipLevelButton, friendshipLevelPosition)) {
                    goToGameMenu(this, GameMenuType.SOCIAL);
                    return true;
                }

                if (clickedOnTexture(screenX, screenY, questsListButton, questsListPosition)) {
                    goToJournal(this);
                    return true;
                }

                npcOptions = null;
                show();
            }
            if (playerOptions != null) {
                if (clickedOnTexture(screenX, screenY, hugButton, hugPosition)) {
                    //todo
                    System.out.println("hug button clicked");
                    return true;
                }

                if (clickedOnTexture(screenX, screenY,proposeButton, proposePosition)) {
                    Result result = controller.askMarriage(playerWithMenu.getUsername());
                    if (!result.success) {
                        errorMessageLabel.setText(result.message);
                        return true;
                    }
                    goToProposeMenu(this, App.getLoggedIn().getUsername(),playerWithMenu.getUsername());
                    HashMap<String, Object> body = new HashMap<>();
                    body.put("id", App.getCurrentGame().getId());
                    body.put("sender", App.getLoggedIn().getUsername());
                    body.put("receiver", playerWithMenu.getUsername());
                    Message message = new Message(body, MessageType.START_PROPOSE);
                    getClient().sendMessage(JSONUtils.toJson(message));
                    return true;
                }

                if (clickedOnTexture(screenX, screenY, giveFlowerButton, giveFlowerPosition)) {
                    Result result = controller.giveFlowerToUser(playerWithMenu.getUsername(), new Good(GoodsType.BOUQUET));
                    if (result.success) {
                        playerWithFlower = playerWithMenu;
                        return true;
                    }
                    errorMessageLabel.setText(result.message);
                    return true;
                }

                npcOptions = null;
                playerOptions = null;
                show();
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        show();
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        super.keyDown(keycode);
        if (keycode == Input.Keys.G) {
            goToGiftPlayerMenu(this);
            return true;
        }
        return false;
    }

    public void addDialogBox(NPC npc) {
        Image dialogBox = new Image(GameAssetManager.getGameAssetManager().getDialogBox());
        dialogBox.setScale(1.15f);
        dialogBox.setPosition(
            (Gdx.graphics.getWidth() - dialogBox.getScaleX() * dialogBox.getWidth()) / 2,
            10
        );
        stage.addActor(dialogBox);

        Image npcPortrait = new Image(GameAssetManager.getGameAssetManager().getNPCPortrait(npc.getType()));
        npcPortrait.setScale(2f);
        npcPortrait.setPosition(
            (Gdx.graphics.getWidth() - dialogBox.getWidth()) / 2 + 950,
            170
        );
        stage.addActor(npcPortrait);

        Label name = new Label(npc.getName(), GameAssetManager.getGameAssetManager().getSkin());
        name.setColor(Color.BLACK);
        name.setFontScale(1.3f);
        name.setPosition(
            npcPortrait.getX() + 40 + name.getWidth() / 2,
            80
        );
        stage.addActor(name);

        String dialog = npc.generateDialog();
        Label dialogLabel = new Label(dialog, GameAssetManager.getGameAssetManager().getSkin());
        dialogLabel.setColor(Color.BLACK);
        dialogLabel.setFontScale(1.3f);
        dialogLabel.setWrap(true);
        dialogLabel.setAlignment(Align.topLeft);
        dialogLabel.setSize(dialogBox.getWidth() * 0.6f, dialogBox.getHeight() * 0.5f);
        dialogLabel.setPosition(
            dialogBox.getX() + dialogBox.getWidth() * 0.1f - 60,
            dialogBox.getY() + dialogBox.getHeight() * 0.4f + 70
        );

        stage.addActor(dialogLabel);
    }

    public void setNpcWithGift(NPC npcWithGift) {
        this.npcWithGift = npcWithGift;
    }

    public void setPlayerWithFlower(User playerWithFlower) {
        this.playerWithFlower = playerWithFlower;
    }

    public void setPlayerWithGift(User playerWithGift) {
        this.playerWithGift = playerWithGift;
    }
}
