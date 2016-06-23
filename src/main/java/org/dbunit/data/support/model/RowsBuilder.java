package org.dbunit.data.support.model;

import org.dbunit.data.support.utils.Preconditions;
import org.dbunit.dataset.Column;

import java.util.HashMap;
import java.util.Map;

public class RowsBuilder {

    private final Map<String, Object> data;

    private int times = 1;

    public RowsBuilder() {
        data = new HashMap<>();
    }

    public RowsBuilder(RowsBuilder rowsBuilder) {
        this.data = new HashMap<>(rowsBuilder.data);
    }

    public RowsBuilder with(Column column, Object value) {
        data.put(column.getColumnName(), value);
        return this;
    }

    public RowsBuilder withNull(Column column) {
        data.put(column.getColumnName(), null);
        return this;
    }

    public RowsBuilder times(int times) {
        Preconditions.checkArgument(times > 0, "The number of repeating values must be > 0");
        this.times = times;
        return this;
    }

    public Row[] build() {
        Row[] rows = new Row[times];
        for (int i = 0; i < times; i++) {
            rows[i] = new Row(data);
        }

        return rows;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RowsBuilder other = (RowsBuilder) o;
        return data.equals(other.data) && times == other.times;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }
}
