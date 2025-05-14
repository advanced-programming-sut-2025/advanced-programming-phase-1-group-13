package models.trade;

import models.App;
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

    @Override
    public String toString() {
        StringBuilder message = new StringBuilder();
        message.append("    ")
                .append(id).append(". Submitted by ")
                .append(creator.getUsername()).append(":\n        ");

        if (requester.equals(App.getLoggedIn())) {
            message.append("Offered ");
        } else if (offerer.equals(App.getLoggedIn())) {
            message.append("Requested ");
        }

        message.append(item.getName()).append("(x").append(amount).append(") for ");

        if (this instanceof TradeWithMoney) {
            message.append(((TradeWithMoney) this).getPrice()).append("g\n");
        } else if (this instanceof TradeWithItem) {
            message.append(((TradeWithItem) this).getTargetItem().getName()).append("(x")
                    .append(amount).append(")\n");
        }

        return message.toString();
    }
}