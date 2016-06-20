package org.dbunit.data.support.tables.tasks;

import org.dbunit.dataset.Column;

import static org.dbunit.dataset.datatype.DataType.BIGINT;
import static org.dbunit.dataset.datatype.DataType.NUMERIC;
import static org.dbunit.dataset.datatype.DataType.SMALLINT;
import static org.dbunit.dataset.datatype.DataType.VARCHAR;

public interface Packages {

    Column ID = new Column("ID", BIGINT);
    Column SUMMARY = new Column("SUMMARY", VARCHAR);
    Column LISTS_LIMIT = new Column("LISTS_LIMIT", SMALLINT);
    Column PRICE = new Column("PRICE", NUMERIC);

    static Column[] getColumns() {
        return new Column[]{ID, SUMMARY, LISTS_LIMIT, PRICE};
    }
}
