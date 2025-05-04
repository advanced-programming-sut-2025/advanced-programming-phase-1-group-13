package views;

import controllers.TradeController;
import models.enums.commands.GameCommands;
import models.enums.commands.TradeCommands;

import java.util.Scanner;
import java.util.regex.Matcher;

public class TradeMenu implements AppMenu {
    Matcher matcher;
    private final TradeController controller = new TradeController();

    @Override
    public void check(Scanner scanner) {
        String inputLine = scanner.nextLine();
        if ((matcher = TradeCommands.TRADE.getMatcher(inputLine)) != null) {
            System.out.println(controller.trade(
                    matcher.group("username"),
                    matcher.group("type"),
                    matcher.group("item"),
                    matcher.group("amount"),
                    matcher.group("price"),
                    matcher.group("targetItem"),
                    matcher.group("targetAmount")
            ));
        } else if ((matcher = TradeCommands.TRADE_LIST.getMatcher(inputLine)) != null) {
            System.out.println(controller.showTradeList());
        } else if ((matcher = TradeCommands.TRADE_RESPONSE.getMatcher(inputLine)) != null) {
            System.out.println(controller.tradeResponse(matcher.group("response"), matcher.group("id")));
        } else if ((matcher = TradeCommands.TRADE_HISTORY.getMatcher(inputLine)) != null) {
            System.out.println(controller.showTradeHistory());
        } else {
            System.out.println("Invalid Command. Please try again!");
        }
    }
}
