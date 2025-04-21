package models.enums.types;

public enum Gender {
    WOMAN,
    MAN,
    RATHER_NOT_SAY;

    public static Gender getGenderByString(String str) {
        if (str == null) {
            return null;
        }
        try {
            return Gender.valueOf(str.toUpperCase());
        } catch (IllegalArgumentException e) {
            return null;
        }
    }
}

