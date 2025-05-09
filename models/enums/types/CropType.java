package models.enums.types;

public enum CropType implements ItemType {
    BLUE_JAZZ("Blue jazz", false),
    CARROT("Carrot", false),
    CAULIFLOWER("Cauliflower", false),
    COFFEE_BEAN("Coffee bean", false),
    GARLIC("Garlic", false),
    GREEN_BEAN("Green bean", false),
    KALE("Kale", false),
    PARSNIP("Parsnip", false),
    POTATO("Potato", false),
    RHUBARB("Rhubarb", false),
    STRAWBERRY("Strawberry", false),
    TULIP("Tulip", false),
    UNMILLED_RICE("Unmilled rice", false),
    BLUEBERRY("Blueberry", false),
    CORN("Corn", false),
    HOPS("Hops", false),
    HOT_PEPPER("Hot pepper", false),
    MELON("Melon", false),
    POPPY("Poppy", false),
    RADISH("Radish", false),
    RED_CABBAGE("Red cabbage", false),
    STARFRUIT("Starfruit", false),
    SUMMER_SPANGLE("Summer spangle", false),
    SUMMER_SQUASH("Summer squash", false),
    SUNFLOWER("Sunflower", false),
    TOMATO("Tomato", false),
    WHEAT("Wheat", false),
    AMARANTH("Amaranth", false),
    ARTICHOKE("Artichoke", false),
    BEET("Beet", false),
    BOK_CHOY("Bok choy", false),
    BROCCOLI("Broccoli", false),
    CRANBERRIES("Cranberries", false),
    EGGPLANT("Eggplant", false),
    FAIRY_ROSE("Fairy rose", false),
    PUMPKIN("Pumpkin", false),
    YAM("Yam", false),
    SWEET_GEM_BERRY("Sweet gem berry", false),
    POWDERMELON("Powdermelon", false),
    ANCIENT_FRUIT("Ancient fruit", false),
    COMMON_MUSHROOM("Common mushroom", true),
    DAFFODIL("Daffodil", true),
    DANDELION("Dandelion", true),
    LEEK("Leek", true),
    MOREL("Morel", true),
    SALMONBERRY("Salmonberry", true),
    SPRING_ONION("Spring onion", true),
    WILD_HORSERADISH("Wild horseradish", true),
    FIDDLEHEAD_FERN("Fiddlehead fern", true),
    GRAPE("Grape", null),
    RED_MUSHROOM("Red mushroom", true),
    SPICE_BERRY("Spice berry", true),
    SWEET_PEA("Sweet pea", true),
    BLACKBERRY("Blackberry", true),
    CHANTERELLE("Chanterelle", true),
    HAZELNUT("Hazelnut", true),
    PURPLE_MUSHROOM("Purple mushroom", true),
    WILD_PLUM("Wild plum", true),
    CROCUS("Crocus", true),
    CRYSTAL_FRUIT("Crystal fruit", true),
    HOLLY("Holly", true),
    SNOW_YAM("Snow yam", true),
    WINTER_ROOT("Winter root", true);

    private final String name;
    private final Boolean isForaging;

    CropType(String name, Boolean isForaging) {
        this.name = name;
        this.isForaging = isForaging;
    }

    @Override
    public String getName() {
        return this.name;
    }
}
