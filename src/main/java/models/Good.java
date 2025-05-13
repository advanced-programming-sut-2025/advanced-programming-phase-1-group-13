package models;

import models.enums.types.GoodsType;

import java.util.HashMap;

public class Good extends Item {
    private final GoodsType type;
    private HashMap<User, Integer> numberSoldToUsersToday;

    public Good(GoodsType type) {
        this.type = type;
        this.numberSoldToUsersToday = new HashMap<>();
        for (User player : App.getCurrentGame().getPlayers()) {
            this.numberSoldToUsersToday.put(player, 0);
        }
    }

    public GoodsType getType() {
        return type;
    }

    public HashMap<User, Integer> getNumberSoldToUsersToday() {
        return numberSoldToUsersToday;
    }

    public void setNumberSoldToUsersToday(User user, int numberSold) {
        this.numberSoldToUsersToday.put(user, numberSold);
    }
}
