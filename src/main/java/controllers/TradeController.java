package controllers;

import models.*;
import models.enums.Menu;

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
        // TODO
        return new Result(true, "");
    }

    public Result tradeResponse(String response, String idStr) {
        // TODO
        return new Result(true, "");
    }

    public Result showTradeHistory() { // type?
        // TODO:
        return new Result(true, "");
    }

    public Result exitTradeMenu() {
        App.setCurrentMenu(Menu.GAME_MENU);
        return new Result(true, "Exiting Trade Menu...");
    }
}
