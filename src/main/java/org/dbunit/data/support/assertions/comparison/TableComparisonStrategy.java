package org.dbunit.data.support.assertions.comparison;

import org.dbunit.dataset.ITable;

public interface TableComparisonStrategy {

    void assertEqual(ITable table1, ITable table2);

}
