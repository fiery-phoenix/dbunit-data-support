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

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        LongSequenceGenerator that = (LongSequenceGenerator) o;

        return current == that.current && increment == that.increment;
    }

    @Override
    public int hashCode() {
        int result = (int) (current ^ (current >>> 32));
        result = 31 * result + (int) (increment ^ (increment >>> 32));

        return result;
    }
}
