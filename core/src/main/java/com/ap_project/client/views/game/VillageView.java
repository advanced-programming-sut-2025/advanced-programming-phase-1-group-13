package com.ap_project.client.views.game;

import com.ap_project.Main;
import com.ap_project.client.controllers.GameController;
import com.ap_project.common.models.App;
import com.ap_project.common.models.GameAssetManager;
import com.ap_project.common.models.NPCVillage;
import com.ap_project.common.models.Shop;
import com.ap_project.common.models.enums.types.NPCType;
import com.ap_project.common.models.enums.types.ShopType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

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
            draw(npcTextures.get(i), App.getCurrentGame().getNpcs().get(i).getPosition());
        }

        Main.getBatch().end();
    }
}
