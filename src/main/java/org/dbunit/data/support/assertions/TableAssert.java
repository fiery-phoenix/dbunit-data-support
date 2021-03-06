package org.dbunit.data.support.assertions;

import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.assertions.columns.ColumnAssert;
import org.dbunit.data.support.assertions.comparison.SimpleTableComparisonStrategy;
import org.dbunit.data.support.assertions.comparison.TableComparisonStrategy;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.data.support.model.Table;
import org.dbunit.data.support.model.TableBuilder;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;

import static org.dbunit.data.support.utils.ConversionUtils.toColumnsNames;
import static org.dbunit.dataset.filter.DefaultColumnFilter.includedColumnsTable;
import static org.junit.Assert.assertEquals;

public class TableAssert {

    private final Table tableDefinition;
    private final ITable actualTable;
    private String[] columns;

    private TableComparisonStrategy comparisonStrategy = SimpleTableComparisonStrategy.getInstance();

    public TableAssert(Table tableDefinition, ITable actualTable) {
        this.tableDefinition = tableDefinition;
        this.actualTable = actualTable;
    }

    public ColumnAssert column(Column column) {
        return column(column.getColumnName());
    }

    public ColumnAssert column(String column) {
        return new ColumnAssert(actualTable, column);
    }

    public void isEmpty() {
        hasSize(0);
    }

    public void hasSize(int size) {
        assertEquals(size, actualTable.getRowCount());
    }

    public TableAssert forColumns(Column... columns) {
        return forColumns(toColumnsNames(columns));
    }

    public TableAssert forColumns(String... columns) {
        this.columns = columns;
        return this;
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
        comparisonStrategy.assertEqual(filterColumns(expectedTable), filterColumns(actualTable));
    }

    public void isEqualTo(IDataSet expectedTable) {
        try {
            comparisonStrategy.assertEqual(filterColumns(expectedTable.getTable(tableDefinition.getName())),
                    filterColumns(actualTable));
        } catch (DatabaseUnitException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

    private ITable filterColumns(ITable table) {
        try {
            return columns == null ? table : includedColumnsTable(table, columns);
        } catch (DataSetException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

    public void isEqualTo(TableBuilder expectedTable) {
        isEqualTo(expectedTable.build(tableDefinition));
    }

}
