package org.dbunit.data.support.assertions;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;

public class IgnoringOrderComparisonStrategy implements TableComparisonStrategy {

    @Override
    public void assertEqual(ITable table1, ITable table2) {
        try {
            Assertion.assertEquals(new SortedTable(table1), new SortedTable(table2));
        } catch (DatabaseUnitException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

}
