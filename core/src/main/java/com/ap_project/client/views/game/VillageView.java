package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.GameController;
import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.types.Dialog;
import com.ap_project.common.models.enums.types.NPCType;
import com.ap_project.common.models.enums.types.ShopType;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Align;

import java.util.ArrayList;

public class VillageView extends GameView {
    private final ArrayList<Texture> shopTextures;
    private final ArrayList<Texture> npcTextures;

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
    }

    @Override
    public void renderMap(float delta) {
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();

        for (int i = 0; i < shopTextures.size(); i++) {
            if (ShopType.values()[i] == ShopType.MARNIE_RANCH) {
                scale = 1f;
            } else {
                scale = 4.400316f;
            }
            draw(shopTextures.get(i), NPCVillage.getShops().get(i).getPosition());
        }

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

        Main.getBatch().end();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        super.touchDown(screenX, screenY, pointer, button);

        for (int i = 0; i < npcTextures.size(); i++) {
            NPC npc = App.getCurrentGame().getNpcs().get(i);
            if (clickedOnTexture(screenX, screenY, npcTextures.get(i), npc.getPosition(), scale)) {
                if (npc.hasDialog()) {
                    Image dialogBox = new Image(GameAssetManager.getGameAssetManager().getDialogBox());
                    dialogBox.setScale(1.2f);
                    dialogBox.setPosition(
                        (Gdx.graphics.getWidth() - dialogBox.getScaleX()*dialogBox.getWidth()) /2,
                        10
                    );
                    stage.addActor(dialogBox);

                    Image npcPortrait = new Image(GameAssetManager.getGameAssetManager().getNPCPortrait(npc.getType()));
                    npcPortrait.setScale(1.7f);
                    npcPortrait.setPosition(
                        (Gdx.graphics.getWidth() - dialogBox.getWidth()) /2 + 730,
                        130
                    );
                    stage.addActor(npcPortrait);

                    Label name = new Label(npc.getName(), GameAssetManager.getGameAssetManager().getSkin());
                    name.setColor(Color.BLACK);
                    name.setFontScale(1.3f);
                    name.setPosition(
                        npcPortrait.getX() + 50,
                        55
                    );
                    stage.addActor(name);

                    Dialog dialog = npc.getDialog();
                    Label dialogLabel = new Label(dialog.getMessage(), GameAssetManager.getGameAssetManager().getSkin());
                    dialogLabel.setColor(Color.BLACK);
                    dialogLabel.setFontScale(1.3f);
                    dialogLabel.setWrap(true);
                    dialogLabel.setAlignment(Align.topLeft);

                    dialogLabel.setSize(dialogBox.getWidth() * 0.65f, dialogBox.getHeight() * 0.5f);

                    dialogLabel.setPosition(
                        dialogBox.getX() + dialogBox.getWidth() * 0.1f - 60,
                        dialogBox.getY() + dialogBox.getHeight() * 0.4f + 70
                    );

                    stage.addActor(dialogLabel);
                }
                return true;
            }
        }

        show();

        return false;
    }
}
