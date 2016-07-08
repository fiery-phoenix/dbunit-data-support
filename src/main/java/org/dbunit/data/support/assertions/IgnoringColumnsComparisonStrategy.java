package org.dbunit.data.support.assertions;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.dataset.ITable;

public class IgnoringColumnsComparisonStrategy implements TableComparisonStrategy {

    private final String[] ignoredColumns;

    public IgnoringColumnsComparisonStrategy(String[] ignoredColumns) {
        this.ignoredColumns = ignoredColumns;
    }

    @Override
    public void assertEqual(ITable table1, ITable table2) {
        try {
            Assertion.assertEqualsIgnoreCols(table1, table2, ignoredColumns);
        } catch (DatabaseUnitException e) {
            throw new DbUnitRuntimeException(e);
        }
    }
}
