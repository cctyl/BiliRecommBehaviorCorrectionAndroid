package io.github.bilirecommand.util;

import com.google.gson.Gson;

public class JsonUtil {
    private static final Gson gson = new Gson();

    public static String toJson(Object object) {
        return gson.toJson(object);
    }

    public static <T> T fromJson(String jsonStr, Class<T> cls) {
        return gson.fromJson(jsonStr, cls);
    }
}
