package org.dbunit.data.support.tables;

import org.dbunit.dataset.Column;

import static org.dbunit.dataset.datatype.DataType.BIGINT;
import static org.dbunit.dataset.datatype.DataType.VARCHAR;

public interface Orders {

    Column ID = new Column("ID", BIGINT);
    Column CUSTOMER_ID = new Column("CUSTOMER_ID", BIGINT);
    Column DESCRIPTION = new Column("DESCRIPTION", VARCHAR);

    static Column[] getColumns() {
        return new Column[]{ID, CUSTOMER_ID, DESCRIPTION};
    }

}
