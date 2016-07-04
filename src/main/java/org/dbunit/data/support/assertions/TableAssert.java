package org.dbunit.data.support.assertions;

import org.dbunit.dataset.ITable;

import static org.junit.Assert.assertEquals;

public class TableAssert {

    private final ITable table;

    public TableAssert(ITable table) {
        this.table = table;
    }

    public void isEmpty() {
        hasSize(0);
    }

    public void hasSize(int size) {
        assertEquals(size, table.getRowCount());
    }

}
