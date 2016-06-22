package org.dbunit.data.support.model;

import org.dbunit.data.support.utils.Preconditions;
import org.dbunit.dataset.Column;

import java.util.ArrayList;
import java.util.List;

import static java.util.Arrays.stream;

public class RowsBuilderByColumns {

    private final String[] columnsNames;

    private final List<Object[]> rows = new ArrayList<>();

    public RowsBuilderByColumns(Column[] columns) {
        this.columnsNames = stream(columns).map(Column::getColumnName).toArray(String[]::new);
    }

    public RowsBuilderByColumns values(Object... values) {
        Preconditions.checkArgument(columnsNames.length == values.length, "The number of values doesn't match the number of columns");
        rows.add(values);

        return this;
    }

    public Row[] build() {
        return rows.stream().map(values -> new Row(columnsNames, values)).toArray(Row[]::new);
    }

}
