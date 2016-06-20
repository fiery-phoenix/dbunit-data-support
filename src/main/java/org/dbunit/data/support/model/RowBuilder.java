package org.dbunit.data.support.model;

import org.dbunit.dataset.Column;

import java.util.HashMap;
import java.util.Map;

public class RowBuilder {

    private Map<String, Object> data = new HashMap<>();

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
}
