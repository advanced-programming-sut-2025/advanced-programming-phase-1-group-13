package com.ap_project.models.trade;

import com.ap_project.models.Game;
import com.ap_project.models.Item;
import com.ap_project.models.User;

public class TradeWithItem extends Trade {
    private final Item targetItem;
    private final int targetAmount;

    public TradeWithItem(Game game, User creator, User offerer, User requester, Item item, int amount, Item targetItem,
                         int targetAmount) {

        super(game, creator, offerer, requester, item, amount);
        this.targetItem = targetItem;
        this.targetAmount = targetAmount;
    }

    public Item getTargetItem() {
        return targetItem;
    }

    public int getTargetAmount() {
        return targetAmount;
    }
}
