package models.enums;

public enum SkillLevel {
    LEVEL_ZERO(0),
    LEVEL_ONE(1),
    LEVEL_TWO(2),
    LEVEL_THREE(3);
    
    private final int number;

    SkillLevel(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }
}
