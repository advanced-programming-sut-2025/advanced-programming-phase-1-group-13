package models;

import models.enums.types.InteractionType;

public class Interaction {
    InteractionType type;
    int XPEffect;

    public Interaction(InteractionType type, int XPEffect) {
        this.type = type;
        this.XPEffect = XPEffect;
    }

    public InteractionType getType() {
        return type;
    }

    public int getXPEffect() {
        return XPEffect;
    }
}
