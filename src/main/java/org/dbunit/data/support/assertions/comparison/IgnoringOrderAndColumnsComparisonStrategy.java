package org.dbunit.data.support.assertions.comparison;

import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.dataset.ITable;

import static org.dbunit.dataset.filter.DefaultColumnFilter.excludedColumnsTable;

public class IgnoringOrderAndColumnsComparisonStrategy implements TableComparisonStrategy {

    private final String[] ignoredColumns;

    public IgnoringOrderAndColumnsComparisonStrategy(String[] ignoredColumns) {
        this.ignoredColumns = ignoredColumns;
    }

    @Override
    public void assertEqual(ITable table1, ITable table2) {
        try {
            IgnoringOrderComparisonStrategy.getInstance().assertEqual(excludedColumnsTable(table1, ignoredColumns),
                    excludedColumnsTable(table2, ignoredColumns));
        } catch (DatabaseUnitException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

}
