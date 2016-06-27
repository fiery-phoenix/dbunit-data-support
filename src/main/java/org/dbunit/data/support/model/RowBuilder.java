package org.dbunit.data.support.model;

import org.dbunit.data.support.generators.ValueGenerator;
import org.dbunit.dataset.Column;

import java.util.HashMap;
import java.util.Map;

public class RowBuilder implements RowsBuilder {

    private final Map<String, Object> data;
    private final Map<String, ValueGenerator<?>> valueGenerators;

    public RowBuilder() {
        data = new HashMap<>();
        valueGenerators = new HashMap<>();
    }

    public RowBuilder(RowBuilder rowBuilder) {
        this.data = new HashMap<>(rowBuilder.data);
        this.valueGenerators = new HashMap<>(rowBuilder.valueGenerators);
    }

    public RowBuilder with(Column column, Object value) {
        return with(column.getColumnName(), value);
    }

    public RowBuilder with(String columnName, Object value) {
        data.put(columnName, value);
        valueGenerators.remove(columnName);

        return this;
    }

    public RowBuilder withNull(Column column) {
        return withNull(column.getColumnName());
    }

    public RowBuilder withNull(String columnName) {
        data.put(columnName, null);
        valueGenerators.remove(columnName);

        return this;
    }

    public RowBuilder withGenerated(Column column, ValueGenerator<?> valueGenerator) {
        return withGenerated(column.getColumnName(), valueGenerator);
    }

    public RowBuilder withGenerated(String columnName, ValueGenerator<?> valueGenerator) {
        valueGenerators.put(columnName, valueGenerator);
        data.remove(columnName);

        return this;
    }

    public RepeatedRowBuilder times(int times) {
        return new RepeatedRowBuilder(this, times);
    }

    Row buildRow() {
        return new Row(data, valueGenerators);
    }

    @Override
    public Row[] build() {
        return new Row[]{new Row(data, valueGenerators)};
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        RowBuilder other = (RowBuilder) o;

        return data.equals(other.data) && valueGenerators.equals(other.valueGenerators);
    }

    @Override
    public int hashCode() {
        int result = data.hashCode();
        result = 31 * result + valueGenerators.hashCode();

        return result;
    }
}
