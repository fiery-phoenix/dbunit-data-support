package org.dbunit.data.support.model;

import org.dbunit.data.support.utils.Preconditions;
import org.dbunit.dataset.Column;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.generate;

public class RowsBuilderByColumns implements RowsBuilder {

    private final String[] columnsNames;

    private final List<List<?>> rows = new ArrayList<>();

    public RowsBuilderByColumns(Column[] columns) {
        this.columnsNames = stream(columns).map(Column::getColumnName).toArray(String[]::new);
    }

    public RowsBuilderByColumns(String[] columnsNames) {
        this.columnsNames = Arrays.copyOf(columnsNames, columnsNames.length);
    }

    public RowsBuilderByColumns values(Object... values) {
        checkValuesNumber(values.length);
        rows.add(Arrays.asList(values));

        return this;
    }

    public RepeatedColumnsValuesBuilder repeatingValues(Object... values) {
        checkValuesNumber(values.length);
        return new RepeatedColumnsValuesBuilder(this, Arrays.asList(values));
    }

    private void checkValuesNumber(int length) {
        Preconditions.checkArgument(columnsNames.length == length, "The number of values doesn't match the number of columns");
    }

    @Override
    public Row[] build() {
        return rows.stream().map(values -> new Row(columnsNames, values)).toArray(Row[]::new);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RowsBuilderByColumns that = (RowsBuilderByColumns) o;

        return Arrays.equals(columnsNames, that.columnsNames) && rows.equals(that.rows);
    }

    @Override
    public int hashCode() {
        int result = Arrays.hashCode(columnsNames);
        result = 31 * result + rows.hashCode();

        return result;
    }

    public static class RepeatedColumnsValuesBuilder {

        private final RowsBuilderByColumns rowsBuilder;
        private final List<?> values;

        private RepeatedColumnsValuesBuilder(RowsBuilderByColumns rowsBuilder, List<?> values) {
            this.rowsBuilder = rowsBuilder;
            this.values = values;
        }

        public RowsBuilderByColumns times(int times) {
            Preconditions.checkArgument(times > 0, "The number of repeating values must be > 0");
            rowsBuilder.rows.addAll(generate(() -> values).limit(times).collect(toList()));

            return rowsBuilder;
        }
    }

}
