package org.dbunit.data.support.tables.tasks;

import org.dbunit.data.support.model.GeneratableColumn;
import org.dbunit.dataset.Column;

import static org.dbunit.data.support.generators.ValueGenerators.constant;
import static org.dbunit.data.support.generators.ValueGenerators.sequence;
import static org.dbunit.dataset.datatype.DataType.BIGINT;
import static org.dbunit.dataset.datatype.DataType.VARCHAR;

public interface Lists {

    Column ID = new GeneratableColumn<>("ID", BIGINT, sequence());
    Column SUMMARY = new GeneratableColumn<>("SUMMARY", VARCHAR, constant("summary"));
    Column COMMENT = new Column("COMMENT", VARCHAR);

    static Column[] getColumns() {
        return new Column[]{ID, SUMMARY};
    }

}
