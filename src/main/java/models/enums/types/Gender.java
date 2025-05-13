package models.enums.types;

public enum Gender {
    WOMAN,
    MAN,
    RATHER_NOT_SAY;

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