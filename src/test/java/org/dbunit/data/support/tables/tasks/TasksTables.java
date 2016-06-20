package org.dbunit.data.support.tables.tasks;

import org.dbunit.data.support.model.ConnectionAwareTable;
import org.dbunit.data.support.utils.ConnectionUtils;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;

import java.util.Arrays;

public enum TasksTables implements ConnectionAwareTable {

    USERS("USERS", Users.getColumns()),
    LISTS("LISTS", Lists.getColumns()),
    TASKS("TASKS", Tasks.getColumns()),
    SHARED_LISTS("SHARED_LISTS", SharedLists.getColumns()),
    PACKAGES("PACKAGES", Packages.getColumns());

    private final String name;

    private final Column[] columns;

    TasksTables(String name, Column[] columns) {
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


    @Override
    public IDatabaseConnection getConnection() {
        return ConnectionUtils.getConnection();
    }

}
