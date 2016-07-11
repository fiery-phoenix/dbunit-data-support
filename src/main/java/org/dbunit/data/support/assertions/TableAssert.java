package org.dbunit.data.support.assertions;

import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.assertions.comparison.SimpleTableComparisonStrategy;
import org.dbunit.data.support.assertions.comparison.TableComparisonStrategy;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.data.support.model.Table;
import org.dbunit.data.support.model.TableBuilder;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;

import static org.dbunit.data.support.utils.ConversionUtils.toColumnsNames;
import static org.junit.Assert.assertEquals;

public class TableAssert {

    private final Table tableDefinition;
    private final ITable actualTable;

    private TableComparisonStrategy comparisonStrategy = SimpleTableComparisonStrategy.getInstance();

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

    public IgnoringColumnsTableAssert ignoring(Column... columns) {
        return ignoring(toColumnsNames(columns));
    }

    public IgnoringColumnsTableAssert ignoring(String... columns) {
        return new IgnoringColumnsTableAssert(this, columns);
    }

    public IgnoringOrderTableAssert ignoringOrder() {
        return new IgnoringOrderTableAssert(this);
    }

    protected TableAssert withComparisonStrategy(TableComparisonStrategy comparisonStrategy) {
        this.comparisonStrategy = comparisonStrategy;
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
