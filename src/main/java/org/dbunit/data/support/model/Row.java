package org.dbunit.data.support.model;

import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITableMetaData;

import java.util.HashMap;
import java.util.Map;

public class Row {

    private final Map<String, Object> data;

    public Row(Map<String, Object> data) {
        this.data = new HashMap<>(data);
    }

    private Object getValue(Column column) {
        return data.get(column.getColumnName());
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
