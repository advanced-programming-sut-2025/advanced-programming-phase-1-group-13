import controllers.GameController;
import models.*;
import models.enums.environment.Season;
import models.enums.environment.Weather;
import models.enums.types.TileType;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

public class TimeAndWeatherTest {

    private static final GameController controller = new GameController();
    private static Game game;

    @BeforeAll
    static void setUp() {
        User player1 = new User("", "", "", "", "", null);
        User player2 = new User("", "", "", "", "", null);
        User player3 = new User("", "", "", "", "", null);
        User player4 = new User("", "", "","", "", null);

        game = new Game(new ArrayList<>(List.of(player1, player2, player3, player4)));
        game.setGameMap(new GameMap(1));
        App.setCurrentGame(game);
        App.addGame(game);
        App.setLoggedIn(player1);
    }

    @Test
    void testShowTimeCommand() {
        String result = controller.showTime().message();
        assertEquals("Time: " + game.getGameState().getTime().getHour() + ":00", result);
    }

    @Test
    void testShowDateCommand() {
        String result = controller.showDate().message();
        assertEquals("Date: " + game.getGameState().getTime().getDayInSeason() + " of " + game.getGameState().getTime().getSeason().getName(), result);
    }

    @Test
    void testShowDatetimeCommand() {
        String result = controller.showDateAndTime().message();
        assertEquals("Time: " + game.getGameState().getTime().getHour() + ":00\n" +
                "Date: " + game.getGameState().getTime().getDayInSeason() + " of " + game.getGameState().getTime().getSeason().getName(), result);
    }

    @Test
    void testShowDayOfTheWeekCommand() {
        String result = controller.showDayOfTheWeek().message();
        assertEquals("Day of the week: " + game.getGameState().getTime().getWeekday().getName(), result);
    }

    @Test
    void testShowSeasonCommand() {
        String result = controller.showSeason().message();
        assertEquals("Season: " + game.getGameState().getTime().getSeason().getName(), result);
    }

    @Test
    void testCheatTimeCommand() {
        String hourIncrease = "5";
        String result = controller.cheatAdvanceTime(hourIncrease).message();
        assertEquals("Time increased by " + hourIncrease + " hours.", result);

        int expectedHour = (game.getGameState().getTime().getHour() + 5) % 24;
    }

    @Test
    void testCheatDateCommand() {
        String dayIncrease = "3";
        String result = controller.cheatAdvanceDate(dayIncrease).message();
        assertEquals("Time increased by " + dayIncrease + " days.", result);

        int expectedDayInSeason = game.getGameState().getTime().getDayInSeason() + 3;
        if (expectedDayInSeason > game.getGameState().getTime().getDayInSeason()) {
            expectedDayInSeason %= game.getGameState().getTime().getDayInSeason();
        }
    }


    @Test
    void testShowWeatherCommand() {
        Weather currentWeather = App.getCurrentGame().getGameState().getCurrentWeather();
        String result = controller.showWeather().message();
        assertEquals("Current weather: " + currentWeather.getName(), result);
    }

    @Test
    void testShowWeatherForecastCommand() {
        String result = controller.showWeatherForecast().message();
        Season currentSeason = App.getCurrentGame().getGameState().getTime().getSeason();
        String forecast = "The forecast for " + currentSeason.getName() + " is " + Season.values()[new Random().nextInt(4)].getName();

        assertTrue(result.startsWith("The forecast for " + currentSeason.getName() + " is "));
        assertTrue(result.contains("Rain") || result.contains("Sunny") || result.contains("Snowy") || result.contains("Stormy"));
    }

    @Test
    void testCheatWeatherSetValid() {
        String newWeather = "Sunny";
        String result = controller.cheatWeatherSet(newWeather).message();
        assertEquals("Weather set to: SUNNY", result);

        assertEquals(Weather.SUNNY, App.getCurrentGame().getGameState().getCurrentWeather());
    }

    @Test
    void testCheatWeatherSetInvalid() {
        String newWeather = "INVALID_WEATHER";
        String result = controller.cheatWeatherSet(newWeather).message();
        assertTrue(result.startsWith("Invalid weather type"));

        Weather currentWeather = App.getCurrentGame().getGameState().getCurrentWeather();
    }

    @Test
    void testCheatThorValid() {
        String xString = "10";
        String yString = "15";
        String result = controller.cheatThor(xString, yString).message();

        assertEquals("Thor has struck (" + xString + ", " + yString + ")!", result);

        Position position = new Position(Integer.parseInt(xString), Integer.parseInt(yString));
        Tile tile = App.getCurrentGame().getGameMap().getTileByPosition(position);
        assertEquals(TileType.NOT_PLOWED_SOIL, tile.getType());
    }

    @Test
    void testCheatThorInvalid() {
        String xString = "100";
        String yString = "100";
        String result = controller.cheatThor(xString, yString).message();

        assertEquals("Invalid position for Thor's strike.", result);
    }
}
