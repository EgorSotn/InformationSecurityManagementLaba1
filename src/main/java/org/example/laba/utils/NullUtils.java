package org.example.laba.utils;

import java.util.concurrent.Callable;

public class NullUtils {
    private NullUtils() {
    }

    public static <T> T getOrDefoult(Callable<T> value, T defaultValue) {
        try {
            return value.call();
        } catch (Exception e) {
            return defaultValue;
        }
    }
}
