package models.enums.types;

public enum InteractionType {
    TALK(20),
    TRADE(50),
    GIFT(0), // TODO: XP depends on gift
    HUG(60),
    GIVE_FLOWER(100),
    MARRIAGE_REQUEST(500);

    int XP;

    InteractionType(int XP) {
        this.XP = XP;
    }
}
