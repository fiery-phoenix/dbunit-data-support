package org.dbunit.data.support.assertions;

import org.dbunit.data.support.assertions.comparison.TableComparisonStrategy;
import org.dbunit.data.support.model.TableBuilder;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;

public class DerivativeEqualToTableAssert {

    private TableAssert tableAssert;

    public DerivativeEqualToTableAssert(TableAssert tableAssert, TableComparisonStrategy comparisonStrategy) {
        this.tableAssert = tableAssert;
        tableAssert.withComparisonStrategy(comparisonStrategy);
    }

    public void isEqualTo(ITable expectedTable) {
        tableAssert.isEqualTo(expectedTable);
    }

    public void isEqualTo(IDataSet expectedTable) {
        tableAssert.isEqualTo(expectedTable);
    }

    public void isEqualTo(TableBuilder expectedTable) {
        tableAssert.isEqualTo(expectedTable);
    }

    protected DerivativeEqualToTableAssert withComparisonStrategy(TableComparisonStrategy comparisonStrategy) {
        tableAssert.withComparisonStrategy(comparisonStrategy);
        return this;
    }

}
