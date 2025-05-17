package models.enums.types;

public enum FishingRodType {
    // todo!!!
    TRAINING("Training", 8, 0.1, 25),
    BAMBOO("Bamboo", 8, 0.5, 25),
    FIBERGLASS("Fiberglass", 6, 0.9, 25),
    IRIDIUM("Iridium", 4, 1.2, 25);

    private final String name;
    private final int energy;
    private final double qualityNumber;
    private final int buyingPrice;

    FishingRodType(String name, int energy, double qualityNumber, int buyingPrice) {
        this.name = name;
        this.energy = energy;
        this.qualityNumber = qualityNumber;
        this.buyingPrice = buyingPrice;
    }

    public String getName() {
        return this.name;
    }

    public int getEnergy() {
        return this.energy;
    }

    public double getQualityNumber() {
        return this.qualityNumber;
    }

    public int getBuyingPrice() {
        return this.buyingPrice;
    }

    public FishingRodType getFishingRodTypeByName(String name) {
        for (FishingRodType type : FishingRodType.values()) {
            if (type.name.equals(name)) {
                return type;
            }
        }
        return null;
    }
}

