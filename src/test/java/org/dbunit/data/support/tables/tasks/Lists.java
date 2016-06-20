package org.dbunit.data.support.tables.tasks;

import org.dbunit.dataset.Column;

import static org.dbunit.dataset.datatype.DataType.BIGINT;
import static org.dbunit.dataset.datatype.DataType.VARCHAR;

public interface Lists {

    Column ID = new Column("ID", BIGINT);
    Column SUMMARY = new Column("SUMMARY", VARCHAR);

    static Column[] getColumns() {
        return new Column[]{ID, SUMMARY};
    }

}
