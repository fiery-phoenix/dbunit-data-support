package org.dbunit.data.support.model;

import org.dbunit.data.support.utils.Preconditions;

import java.util.stream.Stream;

public class RepeatedRowBuilder implements RowsBuilder {

    private final RowBuilder rowBuilder;
    private final int times;

    public RepeatedRowBuilder(RowBuilder rowBuilder, int times) {
        Preconditions.checkArgument(times > 0, "The number of repeating values must be > 0");
        this.rowBuilder = rowBuilder;
        this.times = times;
    }

    @Override
    public Row[] build() {
        Row row = rowBuilder.buildRow();
        return Stream.generate(() -> row).limit(times).toArray(Row[]::new);
    }
}
