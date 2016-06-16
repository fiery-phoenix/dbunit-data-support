package org.dbunit.data.support;

import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.data.support.model.ConnectionAwareTable;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;

import java.sql.SQLException;

import static org.junit.Assert.assertEquals;

public final class DbUnitAssertions {

    private DbUnitAssertions() {
    }

    public static void assertEmpty(ConnectionAwareTable table) {
        assertSize(table, 0);
    }

    public static void assertSize(ConnectionAwareTable table, int size) {
        assertEquals(size, getTable(table).getRowCount());
    }

    private static ITable getTable(ConnectionAwareTable table) {
        try {
            IDataSet actualDataSet = table.getConnection().createDataSet(new String[]{table.getName()});
            return actualDataSet.getTable(table.getName());
        } catch (SQLException | DataSetException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

}
