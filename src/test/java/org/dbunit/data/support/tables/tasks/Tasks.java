package org.dbunit.data.support.tables.tasks;

import org.dbunit.dataset.Column;

import static org.dbunit.dataset.datatype.DataType.BIGINT;
import static org.dbunit.dataset.datatype.DataType.VARCHAR;

public interface Tasks {

    Column ID = new Column("ID", BIGINT);
    Column USER_ID = new Column("USER_ID", BIGINT);
    Column SUMMARY = new Column("SUMMARY", VARCHAR);
    Column DESCRIPTION = new Column("DESCRIPTION", VARCHAR);

    static Column[] getColumns() {
        return new Column[]{ID, USER_ID, SUMMARY, DESCRIPTION};
    }

}
