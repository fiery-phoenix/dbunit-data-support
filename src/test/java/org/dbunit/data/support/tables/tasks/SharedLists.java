package org.dbunit.data.support.tables.tasks;

import org.dbunit.dataset.Column;

import static org.dbunit.dataset.datatype.DataType.BIGINT;

public interface SharedLists {

    Column LIST_ID = new Column("LIST_ID", BIGINT);
    Column USER_ID = new Column("ID", BIGINT);

    static Column[] getColumns() {
        return new Column[]{LIST_ID, USER_ID};
    }

}
