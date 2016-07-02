package org.dbunit.data.support.generators.random;

import org.dbunit.data.support.generators.ValueGenerator;
import org.dbunit.data.support.utils.Preconditions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class RandomValueGenerator<T> implements ValueGenerator<T> {

    private final List<T> values;

    public RandomValueGenerator(T[] values) {
        Preconditions.checkArgument(values.length > 0, "Randomly generated values list must not be empty");
        this.values = Arrays.asList(values);
    }

    public RandomValueGenerator(Collection<T> values) {
        Preconditions.checkArgument(values.size() > 0, "Randomly generated values list must not be empty");
        this.values = new ArrayList<>(values);
    }

    @Override
    public T next() {
        return values.get(ThreadLocalRandom.current().nextInt(values.size()));
    }

}
