package org.dbunit.data.support.model;

import org.dbunit.data.support.generators.ValueGenerator;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITableMetaData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Row {

    private final Map<String, Object> data;
    private final Map<String, ValueGenerator<?>> valueGenerators;

    Row(Map<String, Object> data, Map<String, ValueGenerator<?>> valueGenerators) {
        this.data = new HashMap<>(data);
        this.valueGenerators = new HashMap<>(valueGenerators);
    }

    public Row(String[] columnsNames, List<?> values) {
        this.data = new HashMap<>();
        for (int i = 0; i < columnsNames.length; i++) {
            data.put(columnsNames[i], values.get(i));
        }
        valueGenerators = new HashMap<>();
    }

    private Object getValue(Column column) {
        String columnName = column.getColumnName();
        return data.containsKey(columnName) ? data.get(columnName) :
                valueGenerators.containsKey(columnName) ? valueGenerators.get(columnName).next() :
                        column.getDefaultValue();
    }

    public Object[] getValues(ITableMetaData metaData) throws DataSetException {
        Column[] columns = metaData.getColumns();
        Object[] values = new Object[columns.length];
        int index = 0;
        for (Column column : columns) {
            values[index++] = getValue(column);
        }

        return values;
    }

}
