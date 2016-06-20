package org.dbunit.data.support.utils;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.data.support.model.ConnectionAwareTable;
import org.dbunit.data.support.model.Table;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.h2.tools.RunScript;

import java.nio.charset.Charset;
import java.sql.Connection;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public final class ConnectionUtils {

    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";
    private static final String SCHEMA = "TASKS";

    private static final IDatabaseConnection connection = createConnection();

    private static IDatabaseConnection createConnection() {
        try {
            createSchema();
            return new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD, SCHEMA).getConnection();
        } catch (Exception e) {
            throw new DbUnitRuntimeException(e);
        }
    }

    public static void createSchema() throws SQLException {
        RunScript.execute(JDBC_URL, USER, PASSWORD, "src/test/resources/schema.sql", Charset.defaultCharset(), false);
    }

    public static IDatabaseConnection getConnection() {
        return connection;
    }

    public static Connection connectionMock() {
        Connection connectionMock = mock(Connection.class);
        try {
        Connection realConnection = connection.getConnection();
            when(connectionMock.getMetaData()).thenReturn(realConnection.getMetaData());
        } catch (SQLException e) {
           throw new DbUnitRuntimeException(e);
        }

        return connectionMock;
    }

    public static ConnectionAwareTable connectionAwareTable(Table table, IDatabaseConnection connection) {
        return new ConnectionAwareTable() {
            @Override
            public IDatabaseConnection getConnection() {
                return connection;
            }

            @Override
            public String getName() {
                return table.getName();
            }

            @Override
            public Column[] getColumns() {
                return table.getColumns();
            }
        };
    }

}
