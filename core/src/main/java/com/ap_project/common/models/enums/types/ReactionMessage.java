package com.ap_project.common.models.enums.types;

public enum ReactionMessage {
    REACTION_MESSAGE_1("Hi!"),
    REACTION_MESSAGE_2("Nice!"),
    REACTION_MESSAGE_3("Cute!"),
    REACTION_MESSAGE_4("LOL"),
    REACTION_MESSAGE_5("GG"),
    REACTION_MESSAGE_6("Wow!"),
    REACTION_MESSAGE_7("Cool!"),
    REACTION_MESSAGE_8("<3"),
    REACTION_MESSAGE_9("Ty!"),
    REACTION_MESSAGE_10("Oops!"),
    REACTION_MESSAGE_11("Yay!"),
    REACTION_MESSAGE_12("Haha"),
    REACTION_MESSAGE_13("Oof"),
    REACTION_MESSAGE_14("Yum!"),
    REACTION_MESSAGE_15("Woo!");

    private final String message;

    ReactionMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
