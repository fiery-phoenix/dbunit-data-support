package org.dbunit.data.support.utils;

import java.util.function.Supplier;

public final class Preconditions {

    private Preconditions() {
    }

    public static void checkArgument(boolean condition, String message) {
        if (!condition) {
            throw new IllegalArgumentException(message);
        }
    }

    public static void checkArgument(boolean condition, Supplier<String> message) {
        if (!condition) {
            throw new IllegalArgumentException(message.get());
        }
    }
}
