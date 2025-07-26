package com.ap_project.views;

import com.ap_project.Main;
import com.ap_project.controllers.GameController;
import com.ap_project.models.App;
import com.ap_project.models.GameAssetManager;
import com.ap_project.models.NPCVillage;
import com.ap_project.models.Shop;
import com.ap_project.models.enums.types.ShopType;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

import java.util.ArrayList;

public class VillageView extends GameView {
    private final ArrayList<Texture> shopTextures;

    public VillageView(GameController controller, Skin skin) {
        super(controller, skin);
        this.background = GameAssetManager.getGameAssetManager().getVillage(App.getCurrentGame().getGameState().getTime().getSeason());

        this.shopTextures = new ArrayList<>();
        for (Shop shop : NPCVillage.getShops()) {
            shopTextures.add(GameAssetManager.getGameAssetManager().getShop(shop.getType(), App.getCurrentGame().getGameState().getTime().getSeason()));
        }
    }

    @Override
    public void renderMap() {
        Main.getBatch().setProjectionMatrix(camera.combined);
        Main.getBatch().begin();

        for (int i = 0; i < shopTextures.size(); i++) {
            if (ShopType.values()[i] != ShopType.MARNIE_RANCH) {
                draw(shopTextures.get(i), NPCVillage.getShops().get(i).getPosition());
            }
        }

        Main.getBatch().end();
    }
}
