package org.dbunit.data.support.assertions.comparison;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.dataset.ITable;

public class SimpleTableComparisonStrategy implements TableComparisonStrategy {

    private static final TableComparisonStrategy INSTANCE = new SimpleTableComparisonStrategy();

    @Override
    public void assertEqual(ITable table1, ITable table2) {
        try {
            Assertion.assertEquals(table1, table2);
        } catch (DatabaseUnitException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

    public static TableComparisonStrategy getInstance() {
        return INSTANCE;
    }

}
