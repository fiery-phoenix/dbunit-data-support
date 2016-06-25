package org.dbunit.data.support.generators;

public final class ValueGenerators {

    private ValueGenerators() {
    }

    public static <T> ValueGenerator<T> constant(T value) {
        return () -> value;
    }

    public static LongSequenceGenerator sequence() {
        return new LongSequenceGenerator();
    }

    public static LongSequenceGenerator sequence(long start, long increment) {
        return new LongSequenceGenerator(start, increment);
    }

public static StringSequenceGenerator stringSequence(String prefix) {
    return new StringSequenceGenerator(prefix);
}

    public static StringSequenceGenerator stringSequence(String prefix, long start, long increment) {
        return new StringSequenceGenerator(prefix, start, increment);
    }

}
