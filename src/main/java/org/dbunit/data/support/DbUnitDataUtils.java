package org.dbunit.data.support;

import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.data.support.model.ConnectionAwareTable;
import org.dbunit.data.support.model.RowBuilder;
import org.dbunit.data.support.model.RowsBuilderByColumns;
import org.dbunit.data.support.model.TableBuilder;
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

    public static RowBuilder row() {
        return new RowBuilder();
    }

    public static RowBuilder row(RowBuilder template) {
        return new RowBuilder(template);
    }

    public static RowsBuilderByColumns columns(Column... columns) {
        return new RowsBuilderByColumns(columns);
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

    public static void cleanInsert(ConnectionAwareTable table, RowBuilder... rows) {
        executeOperation(CLEAN_INSERT, table, rows);
    }

    public static void insert(ConnectionAwareTable table, RowBuilder... rows) {
        executeOperation(INSERT, table, rows);
    }

    public static void cleanInsert(ConnectionAwareTable table, RowsBuilderByColumns rows) {
        executeOperation(CLEAN_INSERT, table, rows);
    }

    public static void insert(ConnectionAwareTable table, RowsBuilderByColumns rows) {
        executeOperation(INSERT, table, rows);
    }

    private static void executeOperation(DatabaseOperation dbUnitOperation, ConnectionAwareTable table, RowsBuilderByColumns rows) {
        executeOperation(dbUnitOperation, table.getConnection(), new TableBuilder(rows).build(table));
    }

    private static void executeOperation(DatabaseOperation dbUnitOperation, ConnectionAwareTable table, RowBuilder... rows) {
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
