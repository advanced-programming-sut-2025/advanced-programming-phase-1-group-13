package models.trade;

import models.Item;
import models.User;

public class Trade {
    private final int id;
    private final User offerer;
    private final User requester;
    private final Item item;
    private final int amount;
    private boolean accepted;

    public Trade(User offerer, User requester, Item item, int amount) {
        this.id = 0; // TODO
        this.offerer = offerer;
        this.requester = requester;
        this.item = item;
        this.amount = amount;
        this.accepted = false;
    }

    public int getId() {
        return id;
    }

    public User getOfferer() {
        return offerer;
    }

    public User getRequester() {
        return requester;
    }

    public Item getItem() {
        return item;
    }

    public int getAmount() {
        return amount;
    }

    public boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
}
