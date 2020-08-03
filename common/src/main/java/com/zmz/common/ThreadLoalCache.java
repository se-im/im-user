package com.zmz.common;

import org.slf4j.MDC;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhaomanzhou
 * @date 2020/3/18 10:18 上午
 */
public class ThreadLoalCache {

    private static final ThreadLocal<Map<String, Object>> threadLocal = ThreadLocal.withInitial(HashMap::new);

    public static Object get(String key) {
        return threadLocal.get().getOrDefault(key, null);
    }


    public static void set(String key, Object value) {
        threadLocal.get().put(key, value);

    }

    public static void remove(String key) {
        threadLocal.get().remove(key);
        MDC.remove(key);
    }

    public static void clear() {
        threadLocal.set(new HashMap<>());
    }
}
