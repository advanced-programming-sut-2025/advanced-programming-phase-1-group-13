package models.enums.types;

public enum FruitType {
    APRICOT(59),
    CHERRY(80),
    BANANA(150),
    MANGO(130),
    ORANGE(100),
    PEACH(140),
    APPLE(100),
    POMEGRANATE(140),
    OAK_RESIN(150),
    MAPLE_SYRUP(200),
    PINE_TAR(100),
    SAP(2),
    COMMON_MUSHROOM(40),
    MYSTIC_SYRUP(1000);

    private int price;

    FruitType(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
