package org.dbunit.data.support.assertions.columns;

import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.ITable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.junit.Assert.assertTrue;

public class ColumnAssert {

    private final List<Object> values;

    public ColumnAssert(ITable table, String column) {
        values = new ArrayList<>();
        try {
            for (int row = 0; row < table.getRowCount(); row++) {
                values.add(table.getValue(row, column));
            }
        } catch (DataSetException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

    public void hasNoNullValues() {
        assertTrue(values.stream().allMatch(Objects::nonNull));
    }

    public void hasOnlyNullValues() {
        assertTrue(values.stream().noneMatch(Objects::nonNull));
    }

}
