package org.dbunit.data.support.model;

import org.dbunit.dataset.Column;

import java.util.HashMap;
import java.util.Map;

public class RowBuilder implements RowsBuilder {

    private final Map<String, Object> data;

    public RowBuilder() {
        data = new HashMap<>();
    }

    public RowBuilder(RowBuilder rowBuilder) {
        this.data = new HashMap<>(rowBuilder.data);
    }

    public RowBuilder with(Column column, Object value) {
        data.put(column.getColumnName(), value);
        return this;
    }

    public RowBuilder with(String columnName, Object value) {
        data.put(columnName, value);
        return this;
    }

    public RowBuilder withNull(Column column) {
        data.put(column.getColumnName(), null);
        return this;
    }

    public RowBuilder withNull(String columnName) {
        data.put(columnName, null);
        return this;
    }

    public RepeatedRowBuilder times(int times) {
        return new RepeatedRowBuilder(this, times);
    }

    Row buildRow() {
        return new Row(data);
    }

    @Override
    public Row[] build() {
        return new Row[]{new Row(data)};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        return data.equals(((RowBuilder) o).data);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}
