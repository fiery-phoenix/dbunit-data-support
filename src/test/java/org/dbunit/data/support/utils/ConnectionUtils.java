package org.dbunit.data.support.utils;

import org.dbunit.JdbcDatabaseTester;
import org.dbunit.data.support.exceptions.DbUnitRuntimeException;
import org.dbunit.database.IDatabaseConnection;
import org.h2.tools.RunScript;

import java.nio.charset.Charset;
import java.sql.SQLException;

public final class ConnectionUtils {

    private static final String JDBC_DRIVER = org.h2.Driver.class.getName();
    private static final String JDBC_URL = "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1";
    private static final String USER = "sa";
    private static final String PASSWORD = "";

    private static final IDatabaseConnection connection = createConnection();

    public static IDatabaseConnection createConnection() {
        try {
            createSchema();
            return new JdbcDatabaseTester(JDBC_DRIVER, JDBC_URL, USER, PASSWORD).getConnection();
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
}
