package org.dbunit.data.support.tables.tasks;

import org.dbunit.dataset.Column;

import static org.dbunit.dataset.datatype.DataType.BIGINT;
import static org.dbunit.dataset.datatype.DataType.VARCHAR;

public interface Users {

    Column ID = new Column("ID", BIGINT);
    Column LOGIN = new Column("LOGIN", VARCHAR);
    Column NAME = new Column("NAME", VARCHAR);

    static Column[] getColumns() {
        return new Column[]{ID, LOGIN, NAME};
    }

}
