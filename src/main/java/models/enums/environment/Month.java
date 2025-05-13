package models.enums.environment;

public enum Month {
    PISCES(Season.SPRING, true),
    ARIES(Season.SPRING, false),
    TAURUS(Season.SPRING, false),
    GEMINI(Season.SUMMER, true),
    CANCER(Season.SUMMER, false),
    LEO(Season.SUMMER, false),
    VIRGO(Season.FALL, true),
    LIBRA(Season.FALL, false),
    SCORPIUS(Season.FALL, false),
    SAGITTARIUS(Season.WINTER, true),
    CAPRICORNUS(Season.WINTER, false),
    AQUARIUS(Season.WINTER, false);

    private static final int DAYS_PER_MONTH = 28;
    private final Season season;
    private final boolean firstMonthOfSeason;

    Month(Season season, boolean firstMonthOfSeason) {
        this.season = season;
        this.firstMonthOfSeason = firstMonthOfSeason;
    }

    public Season getSeason() {
        return season;
    }

    public int getDaysInMonth() {
        return DAYS_PER_MONTH;
    }

    public Month next() {
        int nextOrdinal = (this.ordinal() + 1) % values().length;
        return values()[nextOrdinal];
    }

    public boolean isFirstMonthOfSeason() {
        return firstMonthOfSeason;
    }

    public int getDaysInSeason() {
        return 3 * DAYS_PER_MONTH; // 84 days per season
    }

    public boolean isLastMonthOfSeason() {
        int nextOrdinal = (this.ordinal() + 1) % values().length;
        return values()[nextOrdinal].isFirstMonthOfSeason();
    }

    public static Month getFirstMonthOfSeason(Season season) {
        for (Month month : values()) {
            if (month.season == season && month.firstMonthOfSeason) {
                return month;
            }
        }
        throw new IllegalArgumentException("No month found for season: " + season);
    }
}