package org.dbunit.data.support;

import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.data.support.model.ConnectionAwareTable;
import org.dbunit.data.support.model.Row;
import org.dbunit.data.support.model.TableBuilder;
import org.dbunit.data.support.model.Field;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.DataSetException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.operation.DatabaseOperation;

import java.sql.SQLException;
import java.util.Arrays;

import static org.dbunit.operation.DatabaseOperation.CLEAN_INSERT;
import static org.dbunit.operation.DatabaseOperation.DELETE_ALL;
import static org.dbunit.operation.DatabaseOperation.INSERT;

public final class DbUnitDataUtils {

    private DbUnitDataUtils() {
    }

    public static Field with(Column column, Object value) {
        return new Field(column.getColumnName(), value);
    }

    public static Field withNull(Column column) {
        return new Field(column.getColumnName(), null);
    }

    public static Row row(Field... fields) {
        return new Row(fields);
    }

    public static ITable getTable(ConnectionAwareTable table) {
        try {
            IDataSet actualDataSet = table.getConnection().createDataSet(new String[]{table.getName()});
            return actualDataSet.getTable(table.getName());
        } catch (SQLException | DataSetException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

    public static void clean(ConnectionAwareTable table) {
        executeOperation(DELETE_ALL, table);
    }

    public static void clean(ConnectionAwareTable... tables) {
        Arrays.stream(tables).forEach(DbUnitDataUtils::clean);
    }

    public static void cleanInsert(ConnectionAwareTable table, Row... rows) {
        executeOperation(CLEAN_INSERT, table, rows);
    }

    public static void insert(ConnectionAwareTable table, Row... rows) {
        executeOperation(INSERT, table, rows);
    }

    private static void executeOperation(DatabaseOperation dbUnitOperation, ConnectionAwareTable table, Row... rows) {
        executeOperation(dbUnitOperation, table.getConnection(), new TableBuilder(rows).build(table));
    }

    private static void executeOperation(DatabaseOperation dbUnitOperation, IDatabaseConnection connection, IDataSet dataSet) {
        try {
            dbUnitOperation.execute(connection, dataSet);
        } catch (DatabaseUnitException | SQLException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

}
