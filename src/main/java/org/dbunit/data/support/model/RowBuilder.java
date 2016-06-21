package org.dbunit.data.support.model;

import org.dbunit.dataset.Column;

import java.util.HashMap;
import java.util.Map;

public class RowBuilder {

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

    public RowBuilder withNull(Column column) {
        data.put(column.getColumnName(), null);
        return this;
    }

    public Row build() {
        return new Row(data);
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
