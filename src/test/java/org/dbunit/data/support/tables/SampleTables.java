package org.dbunit.data.support.tables;

import org.dbunit.data.support.model.Table;
import org.dbunit.dataset.Column;

import java.util.Arrays;

public enum SampleTables implements Table {

    CUSTOMERS("CUSTOMERS", Customers.getColumns());

    private final String name;

    private final Column[] columns;

    SampleTables(String name, Column[] columns) {
        this.name = name;
        this.columns = columns;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public Column[] getColumns() {
        return Arrays.copyOf(columns, columns.length);
    }

}
