package com.ap_project.common.models.trade;

import com.ap_project.common.models.Game;
import com.ap_project.common.models.Item;
import com.ap_project.common.models.User;

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
