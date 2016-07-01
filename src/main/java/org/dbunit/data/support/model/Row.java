package org.dbunit.data.support.model;

import org.dbunit.data.support.generators.ValueGenerator;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static java.util.stream.Collectors.toSet;
import static java.util.stream.Stream.concat;

public class Row {

    private final Map<String, Object> data;
    private final Map<String, ValueGenerator<?>> valueGenerators;

    Row(Map<String, Object> data, Map<String, ValueGenerator<?>> valueGenerators) {
        this.data = new HashMap<>(data);
        this.valueGenerators = new HashMap<>(valueGenerators);
    }

    public Row(Set<String> columnsNames, List<?> values, Map<String, ValueGenerator<?>> valueGenerators) {
        this.data = new HashMap<>();
        int i = 0;
        for (String columnName : columnsNames) {
            data.put(columnName, values.get(i++));
        }
        this.valueGenerators = valueGenerators;
    }

    private Object getValue(Column column) {
        String columnName = column.getColumnName();
        return data.containsKey(columnName) ? data.get(columnName) :
                valueGenerators.containsKey(columnName) ? valueGenerators.get(columnName).next() :
                        column.getDefaultValue();
    }

    public Object[] getValues(VerifiableTableMetaData metaData) throws DataSetException {
        metaData.verifyColumnsNames(concat(data.keySet().stream(), valueGenerators.keySet().stream()).collect(toSet()));
        Column[] columns = metaData.getColumns();
        Object[] values = new Object[columns.length];
        int index = 0;
        for (Column column : columns) {
            values[index++] = getValue(column);
        }

        return values;
    }

}
