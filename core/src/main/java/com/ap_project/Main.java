package com.ap_project;

import com.ap_project.client.controllers.*;
import com.ap_project.client.controllers.game.GameController;
import com.ap_project.client.controllers.login.*;
import com.ap_project.client.controllers.pregame.*;
import com.ap_project.client.controllers.signup.*;
import com.ap_project.client.models.network.GameClient;
import com.ap_project.client.views.*;
import com.ap_project.client.views.game.*;
import com.ap_project.client.views.login.*;
import com.ap_project.client.views.pregame.*;
import com.ap_project.client.views.signup.*;
import com.ap_project.common.models.*;
import com.ap_project.common.models.enums.types.*;

import com.ap_project.server.controller.LobbyMenuController;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

import java.io.IOException;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms.
 */
public class Main extends Game {
    private static Main main;
    private static SpriteBatch batch;

    @Override
    public void create() {
        main = this;
        batch = new SpriteBatch();
        main.setScreen(new TitleMenuView(new TitleMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
    }

    public static void goToTitleMenu() {
        Main.getMain().setScreen(new TitleMenuView(new TitleMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToSignUpMenu() {
        Main.getMain().setScreen(new SignUpMenuView(new SignUpMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToSecurityQuestionMenu(String username) {
        Main.getMain().setScreen(new SecurityQuestionMenuView(new SecurityQuestionMenuController(username), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToLoginMenu() {
        Main.getMain().setScreen(new LoginMenuView(new LoginController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToForgetPasswordMenu() {
        Main.getMain().setScreen(new ForgetPasswordMenuView(new ForgetPasswordMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToChangePasswordMenu(String username) {
        Main.getMain().setScreen(new ChangePasswordMenuView(new ChangePasswordMenuController(username), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToLobbyMenu() {
        try {
            GameClient client = new GameClient("127.0.0.1", 9999);
            LobbyMenuController controller = new LobbyMenuController(client);
            client.setLobbyMenuController(controller);
            Main.getMain().setScreen(new LobbyMenuView(controller, GameAssetManager.getGameAssetManager().getSkin()));
        } catch (IOException e) {
            System.out.println("Failed to connect to lobby server: " + e.getMessage());
            e.printStackTrace();
        }
    }

    public static void goToMainMenu() {
        Main.getMain().setScreen(new MainMenuView(new MainMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToProfileMenu() {
        Main.getMain().setScreen(new ProfileMenuView(new ProfileMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToPreGameMenu() {
        Main.getMain().setScreen(new PreGameMenuView(new PreGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToScoreboard() {
        Main.getMain().setScreen(new ScoreboardView(new ScoreboardController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToNewGameMenu() {
        Main.getMain().setScreen(new NewGameMenuView(new NewGameMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToChooseMapMenu() {
        Main.getMain().setScreen(new ChooseMapMenuView(new ChooseMapMenuController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToGame(GameView gameView) {
        Main.getMain().setScreen(gameView);
    }

    public static void goToFarmhouse() {
        Main.getMain().setScreen(new FarmhouseView(new GameController(), GameAssetManager.getGameAssetManager().getSkin()));
    }

    public static void goToFarmOverview(String description, ItemType itemType, GameView gameView) {
        Main.getMain().setScreen(new FarmOverviewView(description, itemType, gameView));
    }

    public static void goToGameMenu(GameView gameView, GameMenuType gameMenuType) {
        Main.getMain().setScreen(new GameMenuView(gameView, gameMenuType));
    }

    public static void goToJournal(GameView gameView) {
        Main.getMain().setScreen(new JournalView(gameView));
    }

    public static void goToReactionMenu(GameView gameView) {
        Main.getMain().setScreen(new ReactionMenuView(gameView));
    }

    public static void goToRadioMenu(GameView gameView) {
        Main.getMain().setScreen(new RadioMenuView(gameView));
    }

    public static void goToChatMenu(GameView gameView) {
        Main.getMain().setScreen(new ChatMenuView(gameView));
    }

    public static void goToGiveGiftMenu(VillageView villageView, NPC npc) {
        Main.getMain().setScreen(new GiveGiftMenuView(villageView, npc));
    }
    public static void goToVotingMenu(GameView gameView) {
        Main.getMain().setScreen(new VotingMenuView(gameView));
    }

    public static void goToTradeMenu(GameView gameView) {
        Main.getMain().setScreen(new TradeMenuView(gameView));
    }

    public static void goToShopMenu(GameView gameView, Shop shop) {
        Main.getMain().setScreen(new ShopMenuView(gameView, shop));
    }

    public static void goToPurchaseMenu(GameView gameView, Shop shop, Good good) {
        Main.getMain().setScreen(new PurchaseMenuView(gameView, shop, good));
    }

    public static void goToSellMenu(FarmView farmView, ShippingBin shippingBin) {
        Main.getMain().setScreen(new SellMenuView(farmView, shippingBin));
    }

    public static void goToCookingMenu(GameView gameView) {
        Main.getMain().setScreen(new CookingMenuView(gameView));
    }

    public static void goToToolMenu(GameView gameView) {
        Main.getMain().setScreen(new ToolMenuView(gameView));
    }

    public static void goToRefrigeratorMenu(GameView gameView) {
        Main.getMain().setScreen(new RefrigeratorMenuView(gameView));
    }

    public static void goToCropInfoMenu(GameView gameView, String crop) {
        Main.getMain().setScreen(new CropInfoView(gameView, crop));
    }

    public static void goToAnimalMenu(FarmView farmView, Animal animal) {
        Main.getMain().setScreen(new AnimalMenuView(farmView, animal));
    }

    public static void goToAnimalLivingSpaceMenu(FarmView farmView, AnimalLivingSpace animalLivingSpace) {
        Main.getMain().setScreen(new AnimalLivingSpaceMenuView(farmView, animalLivingSpace));
    }

    public static void goToBuyAnimalsMenu(GameView gameView) {
        Main.getMain().setScreen(new BuyAnimalsMenuView(gameView));
    }

    public static void goToBuildMenu(GameView gameView) {
        Main.getMain().setScreen(new BuildMenuView(gameView));
    }

    public static void goToFishingMiniGameMenu(FarmView farmView, Fish fish) {
        Main.getMain().setScreen(new FishingMiniGameView(farmView, fish));
    }

    public static void goToArtisanInfo(FarmView farmView, Artisan artisan) {
        Main.getMain().setScreen(new ArtisanInfoView(farmView, artisan));
    }

    public static void goToArtisanMenu(FarmView farmView, Artisan artisan) {
        Main.getMain().setScreen(new ArtisanMenuView(farmView, artisan));
    }

    public static void goToCheatWindow(GameView gameView, GameController controller) {
        Main.getMain().setScreen(new CheatView(gameView, controller));
    }


    public static void goToMap(GameView gameView) {
        Main.getMain().setScreen(new MapView(gameView));
    }

    public static Main getMain() {
        return main;
    }

    public static void setMain(Main main) {
        Main.main = main;
    }

    public static SpriteBatch getBatch() {
        return batch;
    }
}
