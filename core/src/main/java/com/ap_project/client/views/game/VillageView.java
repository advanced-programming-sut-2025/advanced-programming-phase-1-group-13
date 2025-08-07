package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.GameController;
import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.types.Dialog;
import com.ap_project.common.models.enums.types.NPCType;
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

import static com.ap_project.Main.goToGameMenu;
import static com.ap_project.Main.goToJournal;

public class VillageView extends GameView {
    private final ArrayList<Texture> shopTextures;
    private final ArrayList<Texture> npcTextures;
    private NPC npcWithMenu;
    private Texture npcOptions;
    private final Texture giveGiftButton;
    private Vector2 giveGiftPosition;
    private final Texture QuestsListButton;
    private Vector2 QuestsListPosition;
    private final Texture friendshipLevelButton;
    private Vector2 FriendshipLevelPosition;

    public VillageView(GameController controller, Skin skin) {
        super(controller, skin);
        this.background = GameAssetManager.getGameAssetManager().getVillage(App.getCurrentGame().getGameState().getTime().getSeason());

        this.shopTextures = new ArrayList<>();
        for (Shop shop : NPCVillage.getShops()) {
            shopTextures.add(GameAssetManager.getGameAssetManager().getShop(shop.getType(), App.getCurrentGame().getGameState().getTime().getSeason()));
        }

        this.npcTextures = new ArrayList<>();
        for (NPCType npcType : NPCType.values()) {
            npcTextures.add(GameAssetManager.getGameAssetManager().getNPC(npcType));
        }

        this.npcOptions = null;

        this.friendshipLevelButton = GameAssetManager.getGameAssetManager().getFriendshipLevelButton();
        this.giveGiftButton = GameAssetManager.getGameAssetManager().getGiveGiftButton();
        this.QuestsListButton = GameAssetManager.getGameAssetManager().getQuestsListButton();
    }

    @Override
    public void renderMap(float delta) {
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();

        for (int i = 0; i < shopTextures.size(); i++) {
            scale = 4.400316f;
            draw(shopTextures.get(i), NPCVillage.getShops().get(i).getPosition());
        }

        scale = 4;
        for (int i = 0; i < npcTextures.size(); i++) {
            NPC npc = App.getCurrentGame().getNpcs().get(i);
            draw(npcTextures.get(i), npc.getPosition());

            if (npc.hasDialog()) {
                Texture dialogIcon = GameAssetManager.getGameAssetManager().getDialogIcon();
                float temp = scale;
                scale = 1f;
                draw(dialogIcon, new Position(npc.getPosition().getX(), npc.getPosition().getY() - 2));
                scale = temp;
            }
        }

        if (npcOptions != null) {
            scale = 1f;
            Position position = new Position(npcWithMenu.getPosition());
            position.setY(position.getY() + 3);
            position.setX(position.getX() + 1);
            draw(npcOptions, position);

            // todo
            giveGiftPosition = new Vector2(TILE_SIZE * (position.getX() + 0.35f), TILE_SIZE * (position.getY() - 2.4f));
            QuestsListPosition = new Vector2(TILE_SIZE * (position.getX() + 0.35f), TILE_SIZE * (position.getY() - 1.4f));
            FriendshipLevelPosition = new Vector2(TILE_SIZE * (position.getX() + 0.35f), TILE_SIZE * (position.getY() - 0.4f));

            draw(giveGiftButton, giveGiftPosition);
            draw(QuestsListButton, QuestsListPosition);
            draw(friendshipLevelButton, FriendshipLevelPosition);
        }

        scale = 4.400316f;

        Main.getBatch().end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

        if (button == Input.Buttons.RIGHT) {
            for (int i = 0; i < npcTextures.size(); i++) {
                NPC npc = App.getCurrentGame().getNpcs().get(i);
                if (clickedOnTexture(screenX, screenY, npcTextures.get(i), npc.getPosition(), scale)) {
                    npcOptions = GameAssetManager.getGameAssetManager().getThreeOptions();
                    npcWithMenu = npc;
                }
            }
        }

        for (int i = 0; i < npcTextures.size(); i++) {
            NPC npc = App.getCurrentGame().getNpcs().get(i);
            Position position = new Position(npc.getPosition());
            position.setY(position.getY() - 2);
            if (clickedOnTexture(screenX, screenY, GameAssetManager.getGameAssetManager().getDialogIcon(), position, scale)) {
                if (npc.hasDialog()) {
                    addDialogBox(npc);
                }
                return true;
            }
        }

        if (clickedOnTexture(screenX, screenY, giveGiftButton, giveGiftPosition, scale)) {
            // todo
        }

        if (clickedOnTexture(screenX, screenY, friendshipLevelButton, FriendshipLevelPosition,scale)) {
            goToGameMenu(this);
        }

        if (clickedOnTexture(screenX, screenY, QuestsListButton, QuestsListPosition, scale)) {
            goToJournal(this);
        }

        show();

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

        Dialog dialog = npc.getDialog();
        Label dialogLabel = new Label(dialog.getMessage(), GameAssetManager.getGameAssetManager().getSkin());
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
}
