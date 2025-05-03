package models.enums.types;

public enum FishingRodType {
    TRAINING("Training", 8, 0.1),
    BAMBOO("Bamboo", 8, 0.5),
    FIBERGLASS("Fiberglass", 6, 0.9),
    IRIDIUM("Iridium", 4, 1.2),;

    private final String name;
    private final int energy;
    private final double qualityNumber;

    FishingRodType(String name, int energy, double qualityNumber) {
        this.name = name;
        this.energy = energy;
        this.qualityNumber = qualityNumber;
    }

    public String getName() {
        return name;
    }

    public int getEnergy() {
        return energy;
    }

    public double getQualityNumber() {
        return qualityNumber;
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

