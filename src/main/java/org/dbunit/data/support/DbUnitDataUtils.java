package org.dbunit.data.support;

import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.data.support.model.ConnectionAwareTable;
import org.dbunit.data.support.model.Row;
import org.dbunit.data.support.model.TableBuilder;
import org.dbunit.data.support.model.Field;
import org.dbunit.dataset.Column;
import org.dbunit.operation.DatabaseOperation;

import java.sql.SQLException;

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

    public static void clean(ConnectionAwareTable table) {
        executeOperation(DELETE_ALL, table);
    }

    public static void cleanInsert(ConnectionAwareTable table, Row... rows) {
        executeOperation(CLEAN_INSERT, table, rows);
    }

    public static void insert(ConnectionAwareTable table, Row... rows) {
        executeOperation(INSERT, table, rows);
    }

    private static void executeOperation(DatabaseOperation dbUnitOperation, ConnectionAwareTable table, Row... rows) {
        try {
            dbUnitOperation.execute(table.getConnection(), new TableBuilder(rows).build(table));
        } catch (DatabaseUnitException | SQLException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

}
