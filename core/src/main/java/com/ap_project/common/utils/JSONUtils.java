package com.ap_project.common.utils;

import com.ap_project.common.models.network.Message;
import com.ap_project.common.models.network.MessageType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;

public class JSONUtils {
    private static final GsonBuilder gsonBuilder = new GsonBuilder();
    private static final Gson gson;

    static {
        gsonBuilder.setPrettyPrinting();
        gson = gsonBuilder.create();
    }

    public synchronized static String toJson(Message message) {
        return gson.toJson(message);
    }

    public synchronized static Message fromJson(String json) {
        Message message = gson.fromJson(json, Message.class);

        java.lang.reflect.Type type = new com.google.gson.reflect.TypeToken<HashMap<String, Object>>(){}.getType();
        HashMap<String, Object> map = gson.fromJson(json, type);

        message.setType(MessageType.valueOf((String) map.get("type")));

        if (map.containsKey("body")) {
            Object body = map.get("body");
            if (body instanceof HashMap) {
                message.setBody((HashMap<String, Object>) body);
            }
        }

        return message;
    }
}
