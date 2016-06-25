package org.dbunit.data.support.generators;

import org.dbunit.data.support.utils.Preconditions;

public class StringSequenceGenerator implements ValueGenerator<String> {

    private final String prefix;

    private final LongSequenceGenerator longSequenceGenerator;

    private StringSequenceGenerator(String prefix, LongSequenceGenerator longSequenceGenerator) {
        Preconditions.checkArgument(prefix != null, "String sequence prefix must be not null");
        this.prefix = prefix;
        this.longSequenceGenerator = longSequenceGenerator;
    }

    public StringSequenceGenerator(String prefix) {
        this(prefix, new LongSequenceGenerator());
    }

    public StringSequenceGenerator(String prefix, long start, long increment) {
        this(prefix, new LongSequenceGenerator(start, increment));
    }

    public StringSequenceGenerator startAt(long start) {
        return new StringSequenceGenerator(prefix, longSequenceGenerator.startAt(start));
    }

    public StringSequenceGenerator incrementingBy(long increment) {
        return new StringSequenceGenerator(prefix, longSequenceGenerator.incrementingBy(increment));
    }

    @Override
    public String next() {
        return prefix + longSequenceGenerator.next();
    }
}
