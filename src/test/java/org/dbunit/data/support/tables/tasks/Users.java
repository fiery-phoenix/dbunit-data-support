package org.dbunit.data.support.tables.tasks;

import org.dbunit.data.support.model.GeneratableColumn;
import org.dbunit.dataset.Column;

import static org.dbunit.data.support.generators.ValueGenerators.constant;
import static org.dbunit.data.support.generators.ValueGenerators.stringSequence;
import static org.dbunit.dataset.datatype.DataType.BIGINT;
import static org.dbunit.dataset.datatype.DataType.VARCHAR;

public interface Users {

    Column ID = new Column("ID", BIGINT);
    Column LOGIN = new GeneratableColumn<>("LOGIN", VARCHAR, stringSequence("test"));
    Column NAME = new GeneratableColumn<>("NAME", VARCHAR, constant("Test"));

    static Column[] getColumns() {
        return new Column[]{ID, LOGIN, NAME};
    }

}
