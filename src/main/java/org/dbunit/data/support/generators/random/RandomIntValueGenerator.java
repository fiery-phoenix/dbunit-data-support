package org.dbunit.data.support.generators.random;

import org.dbunit.data.support.generators.ValueGenerator;
import org.dbunit.data.support.utils.Preconditions;

import java.util.concurrent.ThreadLocalRandom;

public class RandomIntValueGenerator implements ValueGenerator<Integer> {

    private final int from;
    private final int delta;

    public RandomIntValueGenerator(int from, int to) {
        Preconditions.checkArgument(from <= to,
                "Randomly generated int value range start value should be less or equal to closing value");
        this.from = from;
        this.delta = to - from + 1;
    }

    @Override
    public Integer next() {
        return from + ThreadLocalRandom.current().nextInt(delta);
    }

}
