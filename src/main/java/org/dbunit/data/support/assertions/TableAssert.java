package org.dbunit.data.support.assertions;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.data.support.model.Table;
import org.dbunit.data.support.model.TableBuilder;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;

import static org.junit.Assert.assertEquals;

public class TableAssert {

    private final Table tableDefinition;
    private final ITable actualTable;

    public TableAssert(Table tableDefinition, ITable actualTable) {
        this.tableDefinition = tableDefinition;
        this.actualTable = actualTable;
    }

    public void isEmpty() {
        hasSize(0);
    }

    public void hasSize(int size) {
        assertEquals(size, actualTable.getRowCount());
    }

    public void isEqualTo(ITable expectedTable) {
        try {
            Assertion.assertEquals(expectedTable, actualTable);
        } catch (DatabaseUnitException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

    public void isEqualTo(IDataSet expectedTable) {
        try {
            Assertion.assertEquals(expectedTable.getTable(tableDefinition.getName()), actualTable);
        } catch (DatabaseUnitException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

    public void isEqualTo(TableBuilder expectedTable) {
        isEqualTo(expectedTable.build(tableDefinition));
    }

}
