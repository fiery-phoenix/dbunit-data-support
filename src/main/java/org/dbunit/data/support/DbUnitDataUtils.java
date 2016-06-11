package org.dbunit.data.support;

import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.data.support.model.ConnectionAwareTable;
import org.dbunit.data.support.model.ConnectionAwareTableBuilder;
import org.dbunit.data.support.model.Field;
import org.dbunit.dataset.Column;
import org.dbunit.operation.DatabaseOperation;

import java.sql.SQLException;

import static org.dbunit.operation.DatabaseOperation.CLEAN_INSERT;
import static org.dbunit.operation.DatabaseOperation.INSERT;

public final class DbUnitDataUtils {

    private DbUnitDataUtils() {
    }

    public static ConnectionAwareTableBuilder table(ConnectionAwareTable table) {
        return new ConnectionAwareTableBuilder(table);
    }

    public static Field with(Column column, Object value) {
        return new Field(column.getColumnName(), value);
    }

    public static Field withNull(Column column) {
        return new Field(column.getColumnName(), null);
    }

    public static void cleanInsert(ConnectionAwareTableBuilder builder) {
        executeOperation(CLEAN_INSERT, builder);
    }

    public static void insert(ConnectionAwareTableBuilder builder) {
        executeOperation(INSERT, builder);
    }

    public static void executeOperation(DatabaseOperation dbUnitOperation, ConnectionAwareTableBuilder builder) {
        try {
            dbUnitOperation.execute(builder.getConnection(), builder.build());
        } catch (DatabaseUnitException | SQLException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

}
