package com.ap_project.common.models.enums.types;

public enum Gender {
    WOMAN("Woman"),
    MAN("Man"),
    RATHER_NOT_SAY("Rather Not Say");

    private final String name;

    Gender(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public static Gender getGenderByName(String genderName) {
        genderName = genderName.toLowerCase();
        switch (genderName) {
            case "man":
            case "m":
            case "male":
                return MAN;
            case "woman":
            case "w":
            case "female":
            case "f":
                return WOMAN;
            default:
                return RATHER_NOT_SAY;
        }
    }
}
