package com.ap_project.common.models.network;

import java.util.HashMap;

public class Message {
    private MessageType type;
    private HashMap<String, Object> body = new HashMap<>();

    public Message() {}

    public Message(HashMap<String, Object> body, MessageType type) {
        this.body = body;
        this.type = type;
    }

    public MessageType getType() {
        return type;
    }

    public void setType(MessageType type) {
        this.type = type;
    }

    public HashMap<String, Object> getBody() {
        return body;
    }

    public void setBody(HashMap<String, Object> body) {
        this.body = body;
    }

    public <T> T getFromBody(String fieldName) {
        return (T) body.get(fieldName);
    }

    @Override
    public String toString() {
        return "{" +
            "type=" + type +
            ", body=" + body +
            '}';
    }
}
