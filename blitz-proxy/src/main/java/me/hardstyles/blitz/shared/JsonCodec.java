package me.hardstyles.blitz.shared;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public final class JsonCodec {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();

    private JsonCodec() {
    }

    public static String toJson(Object value) {
        return GSON.toJson(value);
    }

    public static <T> T fromJson(String value, Class<T> targetType) {
        return GSON.fromJson(value, targetType);
    }
}
