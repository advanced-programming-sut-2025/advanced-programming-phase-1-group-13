package com.ap_project.common.utils;

import com.ap_project.common.models.enums.*;
import com.ap_project.common.models.enums.environment.Direction;
import com.ap_project.common.models.enums.environment.Time;
import com.ap_project.common.models.enums.types.*;
import com.ap_project.common.models.network.Message;
import com.ap_project.common.models.network.MessageType;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;
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
    }}
//
//    private static final Gson gson2 = new GsonBuilder()
//        .setPrettyPrinting()
//        .registerTypeAdapter(Gender.class, new EnumSerializer<Gender>())
//        .registerTypeAdapter(Skill.class, new EnumSerializer<Skill>())
//        .registerTypeAdapter(SkillLevel.class, new EnumSerializer<SkillLevel>())
//        .registerTypeAdapter(Quality.class, new EnumSerializer<Quality>())
//        .registerTypeAdapter(SecurityQuestion.class, new EnumSerializer<SecurityQuestion>())
//        .registerTypeAdapter(Direction.class, new EnumSerializer<Direction>())
//        .registerTypeAdapter(FoodType.class, new EnumSerializer<FoodType>())
//        .registerTypeAdapter(CraftType.class, new EnumSerializer<CraftType>())
//        .registerTypeAdapter(ToolMaterial.class, new EnumSerializer<ToolMaterial>())
//        .registerTypeAdapter(BackpackType.class, new EnumSerializer<BackpackType>())
//        .registerTypeAdapter(AnimalProductType.class, new EnumSerializer<AnimalProductType>())
//        .registerTypeAdapter(GoodsType.class, new EnumSerializer<GoodsType>())
//        .registerTypeAdapter(CropType.class, new EnumSerializer<CropType>())
//        .registerTypeAdapter(MineralType.class, new EnumSerializer<MineralType>())
//        .registerTypeAdapter(IngredientType.class, new EnumSerializer<IngredientType>())
//        .registerTypeAdapter(ReactionMessage.class, new EnumSerializer<ReactionMessage>())
//        .create();
//
//    public static String toJson(Object object) {
//        return gson2.toJson(object);
//    }
//
//    public static <T> T fromJson(String json, Class<T> classOfT) {
//        return gson2.fromJson(json, classOfT);
//    }
//
//
//    public static <T> T fromJson(String json, Type type) {
//        return gson2.fromJson(json, type);
//    }
//
//    public static HashMap<String, Object> createBody(Object... keyValuePairs) {
//        HashMap<String, Object> body = new HashMap<>();
//        for (int i = 0; i < keyValuePairs.length; i += 2) {
//            if (i + 1 < keyValuePairs.length) {
//                body.put(keyValuePairs[i].toString(), keyValuePairs[i + 1]);
//            }
//        }
//        return body;
//    }
//
//    private static class EnumSerializer<T extends Enum<T>> implements com.google.gson.JsonSerializer<T>,
//        com.google.gson.JsonDeserializer<T> {
//        @Override
//        public com.google.gson.JsonElement serialize(T src, Type typeOfSrc,
//                                                     com.google.gson.JsonSerializationContext context) {
//            return new com.google.gson.JsonPrimitive(src.name());
//        }
//
//        @Override
//        public T deserialize(com.google.gson.JsonElement json, Type typeOfT,
//                             com.google.gson.JsonDeserializationContext context) throws com.google.gson.JsonParseException {
//            Class<T> enumType = (Class<T>) typeOfT;
//            return Enum.valueOf(enumType, json.getAsString());
//        }
//    }
//}
