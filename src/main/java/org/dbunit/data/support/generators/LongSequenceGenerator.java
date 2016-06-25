package org.dbunit.data.support.generators;

public class LongSequenceGenerator implements ValueGenerator<Long> {

    private long current;

    private final long increment;

    public LongSequenceGenerator() {
        this(1, 1);
    }

    public LongSequenceGenerator(long start, long increment) {
        this.current = start;
        this.increment = increment;
    }

    public LongSequenceGenerator startAt(long start) {
        return new LongSequenceGenerator(start, current);
    }

    public LongSequenceGenerator incrementingBy(long increment) {
        return new LongSequenceGenerator(current, increment);
    }

    @Override
    public Long next() {
        long nextValue = current;
        current += increment;

        return nextValue;
    }
}
