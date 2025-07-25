package com.ap_project.models;

import com.ap_project.models.enums.environment.Direction;
import com.ap_project.models.enums.environment.Season;
import com.ap_project.models.enums.environment.Weather;
import com.ap_project.models.enums.types.GameMenuType;
import com.ap_project.models.enums.types.Gender;
import com.ap_project.models.enums.types.ShopType;
import com.ap_project.models.tools.Tool;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;

public class GameAssetManager {
    private static final GameAssetManager gameAssetManager = new GameAssetManager();

    public static GameAssetManager getGameAssetManager() {
        return gameAssetManager;
    }

    public Skin getSkin() {
        return new Skin(Gdx.files.internal("Skin/pixthulhu-ui.json"));
    }

    public Texture getLogo() {
        return new Texture(Gdx.files.internal("Images/Logo.png"));
    }

    public Texture getMenuBackground() {
        return new Texture(Gdx.files.internal("Images/MenuBackground.png"));
    }

    public Texture getAvatarBackground() {
        return new Texture(Gdx.files.internal("Images/Avatars/AvatarBackground.png"));
    }

    public Texture getAvatar(int number) {
        return new Texture(Gdx.files.internal("Images/Avatars/Avatar" + number + ".png"));
    }

    public Texture getClock(Weather weather, Season season) {
        String path = "Images/Clock/" + weather.getName() + season.getName() + ".png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getInventoryHotbar() {
        return new Texture(Gdx.files.internal("Images/Inventory/InventoryHotbar.png"));
    }

    public Texture getHotbarSelectedSlot() {
        return new Texture(Gdx.files.internal("Images/Inventory/SelectedSlot.png"));
    }

    public Texture getEnergyBar() {
        return new Texture(Gdx.files.internal("Images/Energy/EnergyBar.png"));
    }

    public Texture getGreenBar() {
        return new Texture(Gdx.files.internal("Images/Energy/GreenBar.png"));
    }

    public Texture getClockArrow() {
        return new Texture(Gdx.files.internal("Images/Clock/Arrow.png"));
    }

    public Texture getTextureByItem(Item item) {
        if (item instanceof Tool) {
            return getTextureByTool((Tool) item);
        }
        // TODO
        return null;
    }

    public Texture getTextureByTool(Tool tool) {
        String name = toPascalCase(tool.getName());
        String material = toPascalCase(tool.getToolMaterial().getName());
        String path = "Images/Tools/" + name + "/" + material + name + ".png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getMenuWindowByType(GameMenuType tab) {
        String gender;
        if (App.getLoggedIn().getGender() == Gender.RATHER_NOT_SAY) {
            gender = "Man";
        } else {
            gender = App.getLoggedIn().getGender().getName();
        }
        String path = "Images/Menu/" + tab.getName() + "Menu" + gender + ".png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getTrashCan() {
        return new Texture(Gdx.files.internal("Images/Menu/TrashCan.png"));
    }

    public Texture getBlackScreen() {
        return new Texture(Gdx.files.internal("Images/Menu/Black.png"));
    }

    public Texture getNumber(int number) {
        return new Texture(Gdx.files.internal("Images/Menu/Number" + number + ".png"));
    }

    public Texture getSkillPoint() {
        return new Texture(Gdx.files.internal("Images/Menu/SkillPoint.png"));
    }

    public Texture getSkillMenuHover(String skillName) {
        return new Texture(Gdx.files.internal("Images/Menu/Hover" + skillName + ".png"));
    }

    public Texture getSocialMenuPage(int number) {
        return new Texture(Gdx.files.internal("Images/Menu/SocialMenuPage" + number + ".png"));
    }

    public Texture getHeart() {
        return new Texture(Gdx.files.internal("Images/Menu/Heart.png"));
    }

    public Texture getCheckedBox() {
        return new Texture(Gdx.files.internal("Images/Menu/CheckedBox.png"));
    }

    public Texture getPlayerFriendship(Gender gender) {
        String genderStr;
        if (gender == Gender.RATHER_NOT_SAY) {
            genderStr = "Man";
        } else {
            genderStr = gender.getName();
        }
        String path = "Images/Menu/PlayerFriendship" + genderStr + ".png";
        return new Texture(Gdx.files.internal(path));
    }

    public Texture getCloseButton() {
        return new Texture(Gdx.files.internal("Images/Menu/CloseButton.png"));
    }

    public Texture getIdlePlayer(Gender gender, Direction direction) {
        String genderStr;
        if (gender == Gender.RATHER_NOT_SAY) {
            genderStr = "Man";
        } else {
            genderStr = gender.getName();
        }

        String path = "Images/Player/" + genderStr + "/" + direction.toString() + "1.png";
        return new Texture(Gdx.files.internal(path));
    }

    public Animation<Texture> getPlayerAnimation(Gender gender, Direction direction) {
        String genderStr;
        if (gender == Gender.RATHER_NOT_SAY) {
            genderStr = "Man";
        } else {
            genderStr = gender.getName();
        }

        Array<Texture> frames = new Array<>();
        for (int i = 1; i <= 4; i++) {
            String path = "Images/Player/" + genderStr + "/" + direction.toString() + i + ".png";
            frames.add(new Texture(Gdx.files.internal(path)));
        }
        return new Animation<>(0.1f, frames);
    }

    public Animation<Texture> getFaintAnimation(Gender gender) {
        String genderStr;
        if (gender == Gender.RATHER_NOT_SAY) {
            genderStr = "Man";
        } else {
            genderStr = gender.getName();
        }

        Array<Texture> frames = new Array<>();
        for (int i = 1; i <= 12; i++) {
            String path = "Images/Player/" + genderStr + "/Faint" + i + ".png";
            frames.add(new Texture(Gdx.files.internal(path)));
        }
        return new Animation<>(0.5f, frames);
    }

    public Texture getFarm(Game game, User user) {
        int index = game.getPlayers().indexOf(user) + 1;
        // TODO: String season = game.getGameState().getTime().getSeason().getName();
        return new Texture(Gdx.files.internal("Images/Map/FarmSpring" + index + ".png"));
    }

    public Texture getCircle() {
        return new Texture(Gdx.files.internal("Images/Player/Circle.png"));
    }

    public Texture getRaindrop() {
        return new Texture(Gdx.files.internal("Images/Weather/Raindrop.png"));
    }

    public Texture getSnowflake() {
        return new Texture(Gdx.files.internal("Images/Weather/Snowflake.png"));
    }

    public Animation<Texture> getLightningAnimation() {
        Array<Texture> frames = new Array<>();
        for (int i = 1; i <= 4; i++) {
            String path = "Images/Weather/Lightning" + i + ".png";
            frames.add(new Texture(Gdx.files.internal(path)));
        }
        return new Animation<>(0.1f, frames);
    }

    public Texture getMap(Game game) {
        int numberOfPlayers = game.getPlayers().size();
        String season = game.getGameState().getTime().getSeason().getName();
        return new Texture(Gdx.files.internal("Images/Map/Map" + season + numberOfPlayers + ".png"));
    }

    public Texture getPlayerIcon(Gender gender) {
        String genderStr;
        if (gender == Gender.RATHER_NOT_SAY) {
            genderStr = "Man";
        } else {
            genderStr = gender.getName();
        }
        return new Texture(Gdx.files.internal("Images/Player/" + genderStr + "/Icon.png"));
    }

    public Texture getShop(ShopType shopType, Season season) {
        String shopName = shopType.getName();
        String seasonStr = season.getName();
        return new Texture(Gdx.files.internal("Images/Map/Village/" + shopName + seasonStr + ".png"));
    }

    public Texture getVillage(Season season) {
        String seasonStr = season.getName();
        return new Texture(Gdx.files.internal("Images/Map/Village/Village" + seasonStr + ".png"));
    }

    public static String toPascalCase(String input) {
        if (input == null || input.isEmpty()) return "";

        input = input.replaceAll("'", "");

        StringBuilder pascal = new StringBuilder();
        for (String word : input.trim().split("\\s+")) {
            if (!word.isEmpty()) {
                pascal.append(Character.toUpperCase(word.charAt(0)));
                if (word.length() > 1) {
                    pascal.append(word.substring(1).toLowerCase());
                }
            }
        }
        return pascal.toString();
    }
}
