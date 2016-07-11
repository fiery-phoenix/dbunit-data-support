package org.dbunit.data.support.assertions;

import org.dbunit.data.support.assertions.comparison.IgnoringColumnsComparisonStrategy;
import org.dbunit.data.support.assertions.comparison.IgnoringOrderAndColumnsComparisonStrategy;

public class IgnoringColumnsTableAssert extends DerivativeEqualToTableAssert {

    private final String[] ignoredColumns;

    public IgnoringColumnsTableAssert(TableAssert tableAssert, String[] ignoredColumns) {
        super(tableAssert, new IgnoringColumnsComparisonStrategy(ignoredColumns));
        this.ignoredColumns = ignoredColumns;
    }

    public DerivativeEqualToTableAssert andOrder() {
        withComparisonStrategy(new IgnoringOrderAndColumnsComparisonStrategy(ignoredColumns));
        return this;
    }

}
