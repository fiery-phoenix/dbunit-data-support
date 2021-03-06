package org.dbunit.data.support;

import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.data.support.model.ConnectionAwareTable;
import org.dbunit.data.support.model.RowsBuilder;
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

    public static RowsBuilderByColumns columns(String... columnsNames) {
        return new RowsBuilderByColumns(columnsNames);
    }

    public static ITable getTable(ConnectionAwareTable table) {
        try {
            IDataSet actualDataSet = table.getConnection().createDataSet(new String[]{table.getName()});
            return actualDataSet.getTable(table.getName());
        } catch (SQLException | DataSetException e) {
            throw new DbUnitRuntimeException(e);
        }
    }

    public static TableBuilder table(RowsBuilder... rows) {
        return new TableBuilder(rows);
    }

    public static void deleteFrom(ConnectionAwareTable table) {
        executeOperation(DELETE_ALL, table);
    }

    public static void deleteFrom(ConnectionAwareTable... tables) {
        Arrays.stream(tables).forEach(DbUnitDataUtils::deleteFrom);
    }

    public static void cleanInsert(ConnectionAwareTable table, RowsBuilder... rows) {
        executeOperation(CLEAN_INSERT, table, rows);
    }

    public static void insert(ConnectionAwareTable table, RowsBuilder... rows) {
        executeOperation(INSERT, table, rows);
    }

    private static void executeOperation(DatabaseOperation dbUnitOperation, ConnectionAwareTable table, RowsBuilder... rows) {
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
