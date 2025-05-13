package models.enums.commands;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public enum GameCommands implements Command {
    EXIT_GAME("^\\s*exit\\s+game\\s*$"),
    NEXT_TURN("^\\s*next\\s+turn\\s*$"),
    TIME("^\\s*time\\s*$"),
    DATE("^\\s*date\\s*$"),
    DATETIME("^\\s*datetime\\s*$"),
    DAY_OF_THE_WEEK("^\\s*day\\s+of\\s+the\\s+week\\s*$"),
    CHEAT_ADV_TIME("^\\s*cheat\\s+advance\\s+time\\s+(?<hourIncrease>\\d+)h\\s*$"),
    CHEAT_ADV_DATE("^\\s*cheat\\s+advance\\s+date\\s+(?<dayIncrease>\\d+)d\\s*$"),
    SEASON("^\\s*season\\s*$"),
    CHEAT_THOR("^\\s*cheat\\s+Thor\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*$"),
    WEATHER("^\\s*weather\\s*$"),
    WEATHER_FORECAST("^\\s*weather\\s+forecast\\s*$"),
    CHEAT_WEATHER_SET("^\\s*cheat\\s+weather\\s+set\\s+(?<type>.+)\\s*$"),
    GREENHOUSE_BUILD("^\\s*greenhouse\\s+build\\s*$"),
    WALK("^\\s*walk\\s+-l\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*$"),
    WALK_CONFIRM("^\\s*walk confirm\\s+(?<yOrN>\\w)\\s*$"),
    PRINT_MAP("^\\s*print\\s+map\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*-s\\s+size\\s+(?<size>\\d+)\\s*$"),
    PRINT_COLORED_MAP("^\\s*print\\s+colored\\s+map\\s+(?<x>\\d+)\\s*,\\s*(?<y>\\d+)\\s*-s\\s+size\\s+(?<size>\\d+)\\s*$"),
    HELP_READING_MAP("^\\s*help\\s+reading\\s+map\\s*$"),
    ENERGY_SHOW("^\\s*energy\\s+show\\s*$"),
    CHEAT_ENERGY_SET("^\\s*energy\\s+set\\s+-v\\s+(?<value>\\d+)\\s*$"),
    CHEAT_ENERGY_UNLIMITED("^\\s*energy\\s+unlimited\\s*$"),
    INVENTORY_SHOW("^\\s*energy\\s+show\\s*"),
    TOOLS_EQUIP("^\\s*tools\\s+equip\\s+(?<toolName>.+)\\s*$"),
    TOOLS_SHOW_CURRENT("^\\s*tools\\s+show\\s+current\\s*$"),
    TOOLS_SHOW_AVAILABLE("^\\s*tools\\s+show\\s+available\\s*$"),
    TOOLS_UPGRADE("^\\s*tools\\s+upgrade\\s+(?<toolsName>.+)\\s*$"),
    TOOLS_USE("^\\s*tools\\s+-d\\s+(?<direction>.+)\\s*$"),
    CRAFT_INFO("^\\s*craftinfo\\s+-n\\s+(?<craftName>.+)\\s*$"),
    PLANT("^\\s*plant\\s+-s(?<seed>.+)\\s+-d\\s+(?<direction>.+)\\s*$"),
    SHOW_PLANT("^\\s*showplant\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*$"),
    FERTILIZE("^\\s*fertilize\\s+-f\\s+(?<fertilizer>.+)\\s+-d\\s+(?<direction>.+)\\s*$"),
    HOWMUCH_WATER("^\\s*howmuch\\s+water\\s*$"),
    CRAFTING_SHOW_RECIPES("^\\s*crafting\\s+show\\s+recipes\\s*$"),
    CRAFTING_CRAFT("^\\s*crafting\\s+craft\\s+(?<itemName>.+)\\s*$"),
    PLACE_ITEM("^\\s*place\\s+item\\s+-n\\s+(?<itemName>.+)\\s+-d\\s+(?<direction>.+)\\s*$"),
    CHEAT_ADD_ITEM("^\\s*cheat\\s+add\\s+item\\s+-n\\s+(?<itemName>.+)\\s*(-c\\s+(?<count>\\d+))?\\s*$"),
    COOKING_REFRIGERATOR("^\\s*cooking\\s+refrigerator\\s+(?<putOrPick>put|pick)\\s+(?<item>.+)\\s*$"),
    COOKING_SHOW_RECIPES("^\\s*cooking\\s+show\\s+recipes\\s*$"),
    COOKING_PREPARE("^\\s*cooking\\s+prepare\\s+(?<recipeName>.+)\\s*$"),
    EAT("^\\s*eat\\s+(?<foodName>.+)\\s*$"),
    BUILD("^\\s*build\\s+-a\\s+(?<buildingName>.+)\\s+-l\\s+(?<x>.+)\\s+(?<y>.+)\\s*$"),
    BUY_ANIMAL("^\\s*buy\\s+animal\\s+-a\\s+(?<animal>.+)\\s+-n\\s+(?<name>.+)\\s*$"),
    PET("^\\s*pet\\s+-n\\s+(?<name>.+)\\s*$"),
    CHEAT_SET_FRIENDSHIP("^\\s*cheat\\s+set\\s+friendship\\s+-n\\s+(?<name>.+)\\s+-c\\s+(?<amount>\\d+)\\s*$"),
    ANIMALS("^\\s*animals\\s*$"),
    SHEPHERD_ANIMALS("^\\s*shepherd\\s+animals\\s+-n\\s+(?<animalName>.+)\\s+-l\\s+(?<x>\\d+)\\s+(?<y>\\d+)\\s*$"),
    FEED_HAY("^\\s*feed\\s+hay\\s+-n\\s+(?<animalName>.+)\\s*$"),
    PRODUCES("^\\s*produces\\s*$"),
    COLLECT_PRODUCE("^\\s*collect\\s+produce\\s+-n\\s+(?<name>.+)\\s*$"),
    SELL_ANIMAL("^\\s*sell\\s+animal\\s+-n\\s+(?<name>.+)\\s*$"),
    FISHING("^\\s*fishing\\s+-p\\s+(?<fishingPole>.+)\\s*$"),
    ARTISAN_USE("^\\s*artisan\\s+use\\s+(?<artisanName>.+)\\s+(?<itemsNames>.+)\\s*$"),
    ARTISAN_GET("^\\s*artisan\\s+get\\s+(?<artisanName>.+)\\s*$"),
    SHOW_ALL_PRODUCTS("^\\s*show\\s+all\\s+products\\s*$"),
    SHOW_ALL_AVAILABLE_PRODUCTS("^\\s*show\\s+all\\s+available\\s+products\\s*$"),
    PURCHASE("^\\s*purchase\\s+(?<productName>.+)\\s+-n\\s+(?<count>\\d+)\\s*$"),
    CHEAT_ADD_DOLLARS("^\\s*cheat\\s+add\\s+(?<count>\\d+)\\s+dollars\\s*$"),
    SELL("^\\s*sell\\s+(?<productName>.+)\\s+-n\\s+(?<count>\\d+)\\s*$"),
    FRIENDSHIPS("^\\s*friendships\\s*$"),
    TALK("^\\s*talk\\s+-u\\s+(?<username>.+)\\s+-m\\s+(?<message>.+)\\s*$"),
    TALK_HISTORY("^\\s*talk\\s+history\\s+-u\\s+(?<username>.+)\\s*$"),
    GIFT("^\\s*gift\\s+-u\\s+(?<username>.+)\\s+-i\\s+(?<item>.+)\\s+-a\\s+(?<amount>\\d+)\\s*$"),
    GIFT_LIST("^\\s*gift\\s+list\\s*$"),
    GIFT_RATE("^\\s*gift\\s+rate\\s+-i\\s+(?<giftNumber>\\d+)\\s+-r\\s+(?<rate>\\d+)\\s*$"),
    GIFT_HISTORY("^\\s*gift\\s+history\\s+-u\\s+(?<username>.+)\\s*$"),
    HUG("^\\s*hug\\s-u\\s+(?<username>.+)\\s*$"),
    FLOWER("^\\s*flower\\s+-u\\s+(?<username>.+)\\s+-f\\s+(?<flowerName>.+)\\s*$"),
    ASK_MARRIAGE("^\\s*ask\\s+marriage\\s+-u\\s+(?<username>.+)\\s+-r\\s+(?<ring>.+)\\s*$"),
    RESPONSE_MARRIAGE("^\\s*respond\\s+-(?<response>accept|reject)\\s+-u\\s+(?<username>.+)\\s*$"),
    START_TRADE("^\\s*start\\s+trade\\s*$"),
    MEETNPC("^\\s*meet\\s+NPC\\s+(?<npcName>.+)\\s*$"),
    GIFTNPC("^\\s*gift\\s+NPC\\s+(?<npcName>.+)\\s+-i\\s+(?<item>.+)\\s*$"),
    FRIENDSHIPNPC_LIST("^\\s*friendship\\s+NPC\\s+list\\s*$"),
    QUESTS_LIST("^\\s*quests\\s+list\\s*$"),
    QUESTS_FINISH("^\\s*quests\\s+finish\\s+-n\\s+(?<npcName>.+)\\s+-i\\s+(?<index>\\d+)\\s*$"),


    // TODO: complete these commands below:
    THROW_ITEM_TO_TRASH(" <itemName> <number>") // number is optional (it's ok to get nothing (null). check controller.

    ;
    private final String regex;
    private final Pattern pattern;

    GameCommands(String regex) {
        this.regex = regex;
        this.pattern = Pattern.compile(this.regex);
    }

    @Override
    public boolean matches(String input) {
        return Command.super.matches(input);
    }

    @Override
    public String getRegex() {
        return this.regex;
    }

    @Override
    public Matcher getMatcher(String input) {
        Matcher matcher = pattern.matcher(input);
        if (matcher.matches()) {
            return matcher;
        }
        return null;
    }
}