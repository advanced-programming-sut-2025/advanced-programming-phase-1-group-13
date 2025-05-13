package models.enums.types;

import java.util.Arrays;
import java.util.List;

public enum AnimalType {
    CHICKEN("Chicken", Arrays.asList(AnimalProductType.CHICKEN_EGG, AnimalProductType.LARGE_CHICKEN_EGG), true,
            Arrays.asList(FarmBuildingType.COOP, FarmBuildingType.BIG_COOP, FarmBuildingType.DELUXE_COOP), 800),
    DUCK("Duck", Arrays.asList(AnimalProductType.DUCK_EGG, AnimalProductType.DUCK_FEATHER), true,
            Arrays.asList(FarmBuildingType.BIG_COOP, FarmBuildingType.DELUXE_COOP), 1200),
    RABBIT("Rabbit", Arrays.asList(AnimalProductType.RABBIT_WOOL, AnimalProductType.RABBIT_FOOT), true,
            List.of(FarmBuildingType.DELUXE_COOP), 8000),
    DINOSAUR("Dinosaur", List.of(AnimalProductType.DINOSAUR_EGG), true,
            List.of(FarmBuildingType.BIG_COOP), 14000),
    COW("Cow", Arrays.asList(AnimalProductType.COW_MILK, AnimalProductType.LARGE_COW_MILK), false,
            Arrays.asList(FarmBuildingType.BARN, FarmBuildingType.BIG_BARN, FarmBuildingType.DELUXE_BARN), 1500),
    GOAT("Goat", Arrays.asList(AnimalProductType.GOAT_MILK, AnimalProductType.LARGE_GOAT_MILK), false,
            Arrays.asList(FarmBuildingType.BIG_BARN, FarmBuildingType.DELUXE_BARN), 4000),
    SHEEP("Sheep", List.of(AnimalProductType.WOOL), false,
            List.of(FarmBuildingType.DELUXE_BARN), 8000),
    PIG("Pig", List.of(AnimalProductType.TRUFFLE), false,
            List.of(FarmBuildingType.DELUXE_BARN), 16000);

    private final String name;
    private final List<AnimalProductType> animalProducts;
    private final boolean livesInCage;
    private final List<FarmBuildingType> livingSpaceTypes;
    private final int price;

    AnimalType(String name, List<AnimalProductType> animalProducts, boolean livesInCage, List<FarmBuildingType> livingSpaceTypes, int price) {
        this.name = name;
        this.animalProducts = animalProducts;
        this.livesInCage = livesInCage;
        this.livingSpaceTypes = livingSpaceTypes;
        this.price = price;
    }

    public static AnimalType getAnimalTypeByName(String name) {
        return switch (name) {
            case "Chicken" -> CHICKEN;
            case "Duck" -> DUCK;
            case "Rabbit" -> RABBIT;
            case "Dinosaur" -> DINOSAUR;
            case "Cow" -> COW;
            case "Goat" -> GOAT;
            case "Sheep" -> SHEEP;
            case "Pig" -> PIG;
            default -> null;
        };
    }

    public String getName() {
        return this.name;
    }

    public List<AnimalProductType> getAnimalProducts() {
        return animalProducts;
    }

    public boolean livesInCage() {
        return livesInCage;
    }

    public List<FarmBuildingType> getLivingSpaceTypes() {
        return livingSpaceTypes;
    }

    public int getPrice() {
        return price;
    }
}
