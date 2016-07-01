package org.dbunit.data.support.model;

import org.dbunit.data.support.generators.ValueGenerator;
import org.dbunit.data.support.utils.Preconditions;
import org.dbunit.dataset.Column;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.Arrays.stream;
import static java.util.stream.Collectors.toCollection;
import static java.util.stream.Collectors.toList;
import static java.util.stream.Stream.generate;
import static org.dbunit.data.support.generators.ValueGenerators.constant;

public class RowsBuilderByColumns implements RowsBuilder {

    private final Set<String> columnsNames;
    private final List<List<?>> rows = new ArrayList<>();

    private final Map<String, ValueGenerator<?>> valueGenerators = new HashMap<>();

    public RowsBuilderByColumns(Column[] columns) {
        this.columnsNames = stream(columns).map(Column::getColumnName).collect(toCollection(LinkedHashSet::new));
    }

    public RowsBuilderByColumns(String[] columnsNames) {
        this.columnsNames = stream(columnsNames).collect(toCollection(LinkedHashSet::new));
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
        Preconditions.checkArgument(columnsNames.size() == length, "The number of values doesn't match the number of columns");
    }

    public RowsBuilderByColumns witDefault(Column column, Object value) {
        return witDefault(column.getColumnName(), value);
    }

    public RowsBuilderByColumns witDefault(String columnName, Object value) {
        return withGenerated(columnName, constant(value));
    }

    public RowsBuilderByColumns withGenerated(Column column, ValueGenerator<?> valueGenerator) {
        return withGenerated(column.getColumnName(), valueGenerator);
    }

    public RowsBuilderByColumns withGenerated(String columnName, ValueGenerator<?> valueGenerator) {
        Preconditions.checkArgument(!columnsNames.contains(columnName),
                "Column " + columnName + " is already present in the set of columns' names");
        valueGenerators.put(columnName, valueGenerator);

        return this;
    }

    @Override
    public Row[] build() {
        return rows.stream().map(values -> new Row(columnsNames, values, valueGenerators)).toArray(Row[]::new);
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

        return columnsNames.equals(that.columnsNames) && rows.equals(that.rows)
                && valueGenerators.equals(that.valueGenerators);
    }

    @Override
    public int hashCode() {
        int result = columnsNames.hashCode();
        result = 31 * result + rows.hashCode();
        result = 31 * result + valueGenerators.hashCode();

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
