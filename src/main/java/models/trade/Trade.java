package models.trade;

import models.Game;
import models.Item;
import models.User;

public abstract class Trade {
    private final int id;
    private final User creator;
    private final User offerer;
    private final User requester;
    private final Item item;
    private final int amount;
    private Boolean accepted; // null if not answered

    public Trade(Game game, User creator, User offerer, User requester, Item item, int amount) {
        this.id = game.getTrades().size() + 1;
        this.creator = creator;
        this.offerer = offerer;
        this.requester = requester;
        this.item = item;
        this.amount = amount;
        this.accepted = null;
    }

    public int getId() {
        return id;
    }

    public User getCreator() {
        return creator;
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

    public Boolean isAccepted() {
        return accepted;
    }

    public void setAccepted(Boolean accepted) {
        this.accepted = accepted;
    }
}
