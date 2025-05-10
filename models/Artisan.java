package models;

import models.enums.types.ArtisanType;

import java.util.ArrayList;

public class Artisan {
    ArtisanType type;

    public Artisan(ArtisanType type) {
        this.type = type;
    }

    public ArtisanType getType() {
        return type;
    }

    public String getDescription() {
        // TODO
        return null;
    }

    public ArrayList<Item> getItems() {
        // TODO
        return null;
    }
}
