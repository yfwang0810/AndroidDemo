package yfwang.androiddemo.utils;

import java.util.HashMap;
import java.util.Map;

public class DataHolder {
    private static Map<String, Object> data = new HashMap<>();

    public static void put(String key, Object object) {
        data.put(key, object);
    }

    public static Object get(String key) {
        return data.get(key);
    }
}