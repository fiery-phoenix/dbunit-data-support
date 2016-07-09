package org.dbunit.data.support.tables.tasks;

import org.dbunit.data.support.model.GeneratableColumn;
import org.dbunit.dataset.Column;

import static org.dbunit.data.support.generators.ValueGenerators.any;
import static org.dbunit.data.support.generators.ValueGenerators.constant;
import static org.dbunit.data.support.generators.ValueGenerators.sequence;
import static org.dbunit.dataset.datatype.DataType.BIGINT;
import static org.dbunit.dataset.datatype.DataType.NUMERIC;
import static org.dbunit.dataset.datatype.DataType.SMALLINT;
import static org.dbunit.dataset.datatype.DataType.VARCHAR;

public interface Packages {

    Column ID = new GeneratableColumn<>("ID", BIGINT, sequence());
    Column SUMMARY = new GeneratableColumn<>("SUMMARY", VARCHAR, constant("summary"));
    Column LISTS_LIMIT = new GeneratableColumn<>("LISTS_LIMIT", SMALLINT, any().between(5, 20));
    Column PRICE = new GeneratableColumn<>("PRICE", NUMERIC, any().between(5, 1000));

    static Column[] getColumns() {
        return new Column[]{ID, SUMMARY, LISTS_LIMIT, PRICE};
    }
}
