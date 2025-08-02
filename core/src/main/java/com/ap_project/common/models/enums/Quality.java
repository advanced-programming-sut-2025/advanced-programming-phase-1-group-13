package com.ap_project.common.models.enums;

public enum Quality {
    NORMAL(1),
    SILVER(1.25),
    GOLD(1.5),
    IRIDIUM(2);

    private final double priceCoefficient;

    Quality(double priceCoefficient) {
        this.priceCoefficient = priceCoefficient;
    }

    public double getPriceCoefficient() {
        return priceCoefficient;
    }

    public static Quality getQualityByNumber(double number) {
        if (number <= 0.5) {
            return Quality.NORMAL;
        } else if (number <= 0.7) {
            return Quality.SILVER;
        } else if (number <= 0.9) {
            return Quality.GOLD;
        }
        return Quality.IRIDIUM;

    }
}
