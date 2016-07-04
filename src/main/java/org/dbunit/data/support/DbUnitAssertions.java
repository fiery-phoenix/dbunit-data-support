package org.dbunit.data.support;

import org.dbunit.data.support.assertions.TableAssert;
import org.dbunit.data.support.model.ConnectionAwareTable;

import static org.dbunit.data.support.DbUnitDataUtils.getTable;

public final class DbUnitAssertions {

    private DbUnitAssertions() {
    }

    public static TableAssert assertThat(ConnectionAwareTable table) {
        return new TableAssert(getTable(table));
    }

}
