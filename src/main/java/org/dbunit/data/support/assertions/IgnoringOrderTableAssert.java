package org.dbunit.data.support.assertions;

import org.dbunit.data.support.assertions.comparison.IgnoringOrderAndColumnsComparisonStrategy;
import org.dbunit.data.support.assertions.comparison.IgnoringOrderComparisonStrategy;
import org.dbunit.dataset.Column;

import static org.dbunit.data.support.utils.ConversionUtils.toColumnsNames;

public class IgnoringOrderTableAssert extends DerivativeEqualToTableAssert {

    public IgnoringOrderTableAssert(TableAssert tableAssert) {
        super(tableAssert, IgnoringOrderComparisonStrategy.getInstance());
    }

    public DerivativeEqualToTableAssert andColumns(Column... columns) {
        return andColumns(toColumnsNames(columns));
    }

    public DerivativeEqualToTableAssert andColumns(String... columns) {
        withComparisonStrategy(new IgnoringOrderAndColumnsComparisonStrategy(columns));
        return this;
    }

}
