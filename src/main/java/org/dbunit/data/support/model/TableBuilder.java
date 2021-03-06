package org.dbunit.data.support.model;

import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.dataset.CachedDataSet;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.DefaultTableMetaData;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.stream.BufferedConsumer;
import org.dbunit.dataset.stream.IDataSetConsumer;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import static java.util.Arrays.stream;

public class TableBuilder {

    private final List<Row> rows = new LinkedList<>();

    public TableBuilder(RowsBuilder... rows) {
        Collections.addAll(this.rows, stream(rows).map(RowsBuilder::build).flatMap(Arrays::stream).toArray(Row[]::new));
    }

    public IDataSet build(Table table) {
        CachedDataSet dataSet = new CachedDataSet();
        IDataSetConsumer consumer = new BufferedConsumer(dataSet);
        Column[] columns = table.getColumns();
        VerifiableTableMetaData metaData = new VerifiableTableMetaData(new DefaultTableMetaData(table.getName(), columns));
        try {
            consumer.startTable(metaData);
            for (Row row : rows) {
                consumer.row(row.getValues(metaData));
            }
            consumer.endTable();
            consumer.endDataSet();
        } catch (DataSetException e) {
            throw new DbUnitRuntimeException(e);
        }

        return dataSet;
    }

}
