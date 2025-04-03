package models;

import models.enums.types.ArtisanType;
import models.enums.types.ItemType;

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

    }

    public ArrayList<Item> getItems() {

    }
}
