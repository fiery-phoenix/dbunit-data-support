package org.dbunit.data.support;

import org.dbunit.data.support.model.ConnectionAwareTable;

import static org.dbunit.data.support.DbUnitDataUtils.getTable;
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

}
