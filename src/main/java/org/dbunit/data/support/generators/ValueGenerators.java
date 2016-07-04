package org.dbunit.data.support.generators;

import org.dbunit.data.support.generators.random.RandomIntValueGenerator;
import org.dbunit.data.support.generators.random.RandomValueGenerator;

import java.util.Collection;

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

    public static RandomValueGeneratorBuilder any() {
        return new RandomValueGeneratorBuilder();
    }

    public static class RandomValueGeneratorBuilder {

        private RandomValueGeneratorBuilder() {
        }

        @SafeVarargs
        public final <T> RandomValueGenerator<T> of(T... values) {
            return new RandomValueGenerator<>(values);
        }

        public <T> RandomValueGenerator<T> of(Collection<T> values) {
            return new RandomValueGenerator<>(values);
        }

        public RandomIntValueGenerator between(int from, int to) {
            return new RandomIntValueGenerator(from, to);
        }

    }

}
