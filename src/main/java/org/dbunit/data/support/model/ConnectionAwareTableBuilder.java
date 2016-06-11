package org.dbunit.data.support.model;

import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.*;
import org.dbunit.dataset.stream.BufferedConsumer;
import org.dbunit.dataset.stream.IDataSetConsumer;

import java.util.LinkedList;
import java.util.List;

public class ConnectionAwareTableBuilder implements ConnectionAware {

    protected final ConnectionAwareTable table;

    private final List<Row> rows = new LinkedList<>();

    public ConnectionAwareTableBuilder(ConnectionAwareTable table) {
        this.table = table;
    }

    @Override
    public IDatabaseConnection getConnection() {
        return table.getConnection();
    }

    public ConnectionAwareTableBuilder withRow(Field... data) {
        rows.add(new Row(data));
        return this;
    }

    public IDataSet build() {
        CachedDataSet dataSet = new CachedDataSet();
        IDataSetConsumer consumer = new BufferedConsumer(dataSet);
        ITableMetaData metaData = new DefaultTableMetaData(table.getName(), table.getColumns());
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
