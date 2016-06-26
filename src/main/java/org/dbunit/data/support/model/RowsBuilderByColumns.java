package org.dbunit.data.support.model;

import org.dbunit.data.support.utils.Preconditions;
import org.dbunit.dataset.Column;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static java.util.Arrays.stream;

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
        Preconditions.checkArgument(columnsNames.length == values.length, "The number of values doesn't match the number of columns");
        rows.add(Arrays.asList(values));

        return this;
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
}
