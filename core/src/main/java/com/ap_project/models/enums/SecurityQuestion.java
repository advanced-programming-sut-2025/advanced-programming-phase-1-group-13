package com.ap_project.models.enums;

public enum SecurityQuestion {
    FAVORITE_PLANET("What's your favorite planet?", 1),
    FIRST_THING_COOKED("What's the first thing you cooked?", 2),
    FIRST_FOREIGN_COUNTRY_VISITED("What's the first foreign country you visited", 3),
    MEMORABLE_PERSON("Who's a memorable person to you? ", 4);

    private final String question;
    private final int assignedNumber;

    SecurityQuestion(String question, int assignedNumber) {
        this.question = question;
        this.assignedNumber = assignedNumber;
    }

    public String getQuestion() {
        return this.question;
    }

    @Override
    public String toString() {
        return assignedNumber + ". " + question;
    }

    public static SecurityQuestion getSecurityQuestionByNumber(int number) {
        for (SecurityQuestion question : SecurityQuestion.values()) {
            if (question.assignedNumber == number) {
                return question;
            }
        }
        return null;
    }

    public static SecurityQuestion getSecurityQuestionByQuestion(String question) {
        for (SecurityQuestion question1 : SecurityQuestion.values()) {
            if (question.equals(question1.question)) {
                return question1;
            }
        }
        return null;
    }

}
