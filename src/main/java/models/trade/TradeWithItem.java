package models.trade;

import models.Item;
import models.User;

public class TradeWithItem extends Trade {
    private final Item targetItem;
    private final int targetAmount;

    public TradeWithItem(User offerer, User requester, Item item, int amount, Item targetItem, int targetAmount) {
        super(offerer, requester, item, amount);
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
