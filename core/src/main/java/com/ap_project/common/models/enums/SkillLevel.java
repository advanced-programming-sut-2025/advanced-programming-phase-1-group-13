package com.ap_project.common.models.enums;

public enum SkillLevel {
    LEVEL_ONE(1),
    LEVEL_TWO(2),
    LEVEL_THREE(3),
    LEVEL_FOUR(4);

    private final int number;

    SkillLevel(int number) {
        this.number = number;
    }

    public int getNumber() {
        return number;
    }

    public int calculateMaxPoints() {
        return this.number * 100 + 50;
    }

    public static SkillLevel getSkillLevelByNumber(int number) {
        for (SkillLevel skillLevel : SkillLevel.values()) {
            if (skillLevel.getNumber() == number) {
                return skillLevel;
            }
        }
        return null;
    }
}
