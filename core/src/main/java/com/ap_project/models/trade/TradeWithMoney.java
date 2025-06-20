package com.ap_project.models.trade;

import com.ap_project.models.Game;
import com.ap_project.models.Item;
import com.ap_project.models.User;

public class TradeWithMoney extends Trade {
    private final int price;

    public TradeWithMoney(Game game, User creator, User offerer, User requester, Item item, int amount, int price) {
        super(game, creator, offerer, requester, item, amount);
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
}
