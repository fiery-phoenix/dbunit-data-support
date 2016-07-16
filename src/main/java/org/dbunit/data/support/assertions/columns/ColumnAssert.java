package org.dbunit.data.support.assertions.columns;

import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.ITableMetaData;
import org.dbunit.dataset.datatype.DataType;
import org.dbunit.dataset.datatype.TypeCastException;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ColumnAssert {

    private final DataType dataType;
    private final List<Object> actualValues;

    public ColumnAssert(ITable table, String column) {
        actualValues = new ArrayList<>();
        try {
            dataType = getColumnDataType(table, column);
            for (int row = 0; row < table.getRowCount(); row++) {
                actualValues.add(table.getValue(row, column));
            }
        } catch (DataSetException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

    private DataType getColumnDataType(ITable table, String column) throws DataSetException {
        ITableMetaData tableMetaData = table.getTableMetaData();
        return tableMetaData.getColumns()[tableMetaData.getColumnIndex(column)].getDataType();
    }

    public void hasNoNullValues() {
        assertTrue(actualValues.stream().allMatch(Objects::nonNull));
    }

    public void hasOnlyNullValues() {
        assertTrue(actualValues.stream().noneMatch(Objects::nonNull));
    }

    public void hasValues(Object... expectedValues) {
        assertEquals(expectedValues.length, actualValues.size());

        for (int i = 0; i < expectedValues.length; i++) {
            assertEqual(expectedValues[i], actualValues.get(i));
        }
    }

    private void assertEqual(Object expected, Object actual) {
        try {
            assertTrue(dataType.compare(expected, actual) == 0);
        } catch (TypeCastException e) {
            throw new DbUnitRuntimeException(e);
        }
    }
}
