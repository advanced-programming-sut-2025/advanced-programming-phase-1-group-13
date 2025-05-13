package controllers;

import models.App;
import models.Result;
import models.enums.Menu;

public class TradeController {
    public Result trade(String username, String typeStr, String itemName, String amountStr, String priceStr,
                        String targetItemName, String targetAmountStr) {
        // TODO
        return new Result(true, "");
    }

    public Result tradeWithMoney(String targetUsername, String type, String itemName, int amount, int price) { // type?
        // TODO
        return new Result(true, "");
    }

    public Result tradeWithItem(String targetUsername, String type, String itemName, int amount, String targetItemName, int targetAmount) { // type?
        // TODO: create a Trade class; int ID, User user1, User user2, Hashmap<Item, Integer>
        return new Result(true, "");
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
