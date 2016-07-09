package org.dbunit.data.support.assertions;

import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.data.support.model.Table;
import org.dbunit.data.support.model.TableBuilder;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;

import static java.util.Arrays.stream;
import static org.junit.Assert.assertEquals;

public class TableAssert {

    private final Table tableDefinition;
    private final ITable actualTable;

    private TableComparisonStrategy comparisonStrategy = SimpleTableComparisonStrategy.INSTANCE;

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

    public TableAssert ignoring(Column... columns) {
        return ignoring(stream(columns).map(Column::getColumnName).toArray(String[]::new));
    }

    public TableAssert ignoring(String... columns) {
        comparisonStrategy = new IgnoringColumnsComparisonStrategy(columns);
        return this;
    }

    public TableAssert ignoringOrder() {
        comparisonStrategy = new IgnoringOrderComparisonStrategy();
        return this;
    }

    public void isEqualTo(ITable expectedTable) {
        comparisonStrategy.assertEqual(expectedTable, actualTable);
    }

    public void isEqualTo(IDataSet expectedTable) {
        try {
            comparisonStrategy.assertEqual(expectedTable.getTable(tableDefinition.getName()), actualTable);
        } catch (DatabaseUnitException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

    public void isEqualTo(TableBuilder expectedTable) {
        isEqualTo(expectedTable.build(tableDefinition));
    }

}
