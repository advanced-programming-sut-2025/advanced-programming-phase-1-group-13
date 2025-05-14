package controllers;

import models.*;
import models.enums.Menu;
import models.trade.Trade;
import models.trade.TradeWithItem;
import models.trade.TradeWithMoney;

public class TradeController {
    public Result trade(String username, String typeStr, String itemName, String amountStr, String priceStr,
                        String targetItemName, String targetAmountStr) {
        User player = App.getLoggedIn();
        if (username.equals(player.getUsername())) {
            return new Result(false, "You can't trade with yourself.");
        }

        Game game = App.getCurrentGame();

        User targetUser = game.getPlayerByUsername(username);
        if (targetUser == null) {
            return new Result(false, "User not found.");
        }

        if (!amountStr.matches("\\d+")) {
            return new Result(false, "Invalid amount.");
        }
        int amount = Integer.parseInt(amountStr);

        Item item = Item.getItemByItemName(itemName);
        if (item == null) {
            return new Result(false, "Item not found.");
        }

        boolean isOffer = typeStr.equals("offer");
        if (priceStr != null) {
            int price = Integer.parseInt(priceStr);
            if (targetItemName != null) {
                return new Result(false, "You can only trade with money or item and not both.");
            }
            return player.tradeWithMoney(targetUser, isOffer, item, amount, price);
        }

        Item targetItem = Item.getItemByItemName(targetItemName);
        if (targetItem == null) {
            return new Result(false, "Target item not found.");
        }

        if (!targetAmountStr.matches("\\d+")) {
            return new Result(false, "Invalid target amount.");
        }
        int targetAmount = Integer.parseInt(targetAmountStr);

        return player.tradeWithItem(targetUser, isOffer, item, amount, targetItem, targetAmount);
    }

    public Result showTradeList() {
        User player = App.getLoggedIn();
        StringBuilder message = new StringBuilder("Unanswered trades:\n");

        for (Trade trade : App.getCurrentGame().getTrades()) {
            if (!trade.getCreator().equals(player) && trade.isAccepted() == null) {
                message.append(trade.toString());
            }
        }

        return new Result(true, message.toString());
    }

    public Result tradeResponse(String response, String idStr) {
        int id = Integer.parseInt(idStr);
        Trade trade = App.getCurrentGame().getTradeById(id);
        if (trade == null) {
            return new Result(false, "Invalid id.");
        }

        Game game = App.getCurrentGame();
        User player = App.getLoggedIn();
        if (!trade.getOfferer().equals(player) && !trade.getRequester().equals(player)) {
            return new Result(false, "You are not in this trade.");
        }

        User targetUser;
        if (trade.getOfferer().equals(player)) {
            targetUser = trade.getRequester();
        } else {
            targetUser = trade.getOfferer();
        }

        if (trade.getCreator().equals(player)) {
            return new Result(false,
                    "You have submitted this trade. Wait for " + targetUser.getUsername() + " to respond.");
        }

        boolean accepted = response.equals("accept");
        trade.setAccepted(accepted);

        if (accepted) {
            if (trade instanceof TradeWithMoney) {
                int price = ((TradeWithMoney) trade).getPrice();
                if (trade.getRequester().getBalance() >= price) {
                    Result result = trade.getOfferer().getBackpack()
                            .removeFromInventory(trade.getItem(), trade.getAmount());
                    if (!result.success()) {
                        if (trade.getRequester().equals(player)) {
                            return new Result(false,
                                    trade.getRequester().getUsername() + " doesn't have enough of " +
                                            trade.getItem().getName() + ".");
                        } else {
                            return new Result(false, "You don't have enough of " +
                                    trade.getItem().getName() + ".");
                        }
                    }
                } else {
                    if (trade.getRequester().equals(player)) {
                        return new Result(false, "You don't have enough money.");
                    }
                    return new Result(false,
                            trade.getRequester().getUsername() + " doesn't have enough money.");
                }
            } else {
                TradeWithItem tradeWithItem = (TradeWithItem) trade;
                Result result = trade.getRequester().getBackpack()
                        .removeFromInventory(tradeWithItem.getTargetItem(), tradeWithItem.getTargetAmount());
                if (!result.success()) {
                    if (trade.getRequester().equals(player)) {
                        return new Result(false, "You don't have enough of " +
                                        trade.getItem().getName() + ".");
                    } else {
                        return new Result(false, trade.getRequester().getUsername() +
                                "doesn't have enough of " + trade.getItem().getName() + ".");
                    }
                }

                result = trade.getOfferer().getBackpack().removeFromInventory(trade.getItem(), trade.getAmount());
                if (!result.success()) {
                    trade.getRequester().getBackpack()
                            .addToInventory(tradeWithItem.getTargetItem(), tradeWithItem.getTargetAmount());
                    if (trade.getRequester().equals(player)) {
                        return new Result(false, trade.getRequester().getUsername() +
                                "doesn't have enough of " + trade.getItem().getName() + ".");
                    } else {
                        return new Result(false, "You don't have enough of " +
                                trade.getItem().getName() + ".");
                    }
                }
            }

            trade.setAccepted(true);
            game.changeFriendship(player, targetUser, 50);
            return new Result(true, "You have accepted this trade. Your friendship with them is now " +
                    game.getUserFriendship(player, targetUser).toString() + ".");
        }

        trade.setAccepted(false);
        game.changeFriendship(player, targetUser, -50);
        return new Result(true, "You rejected this trade. Your friendship with them is now " +
                game.getUserFriendship(player, targetUser).toString() + ".");
    }

    public Result showTradeHistory() {
        User player = App.getLoggedIn();

        StringBuilder message = new StringBuilder("""
                Trade History:
                ---------------------------------------------------------------------
                    Trades Submitted by You:
                """);
        for (Trade trade : App.getCurrentGame().getTrades()) {
            if (trade.getCreator().equals(player)) {
                message.append("    ").append(trade.toString());
            }
        }

        message.append("""
                ---------------------------------------------------------------------
                    Trades accepted by You:
                """);
        for (Trade trade : App.getCurrentGame().getTrades()) {
            if (!trade.getCreator().equals(player) && trade.isAccepted()) {
                message.append("    ").append(trade.toString());
            }
        }

        return new Result(true, message.toString());
    }

    public Result exitTradeMenu() {
        App.setCurrentMenu(Menu.GAME_MENU);
        return new Result(true, "Exiting Trade Menu...");
    }
}
