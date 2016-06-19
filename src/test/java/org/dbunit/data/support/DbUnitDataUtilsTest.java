package org.dbunit.data.support;

import org.dbunit.Assertion;
import org.dbunit.DatabaseUnitException;
import org.dbunit.data.support.model.Field;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.mockito.InOrder;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dbunit.data.support.DbUnitDataUtils.clean;
import static org.dbunit.data.support.DbUnitDataUtils.cleanInsert;
import static org.dbunit.data.support.DbUnitDataUtils.insert;
import static org.dbunit.data.support.DbUnitDataUtils.row;
import static org.dbunit.data.support.DbUnitDataUtils.getTable;
import static org.dbunit.data.support.DbUnitDataUtils.with;
import static org.dbunit.data.support.DbUnitDataUtils.withNull;
import static org.dbunit.data.support.tables.Customers.FIRST_NAME;
import static org.dbunit.data.support.tables.Customers.ID;
import static org.dbunit.data.support.tables.Customers.LAST_NAME;
import static org.dbunit.data.support.tables.SampleTables.CUSTOMERS;
import static org.dbunit.data.support.tables.SampleTables.ORDERS;
import static org.dbunit.data.support.utils.ConnectionUtils.connectionAwareTable;
import static org.dbunit.data.support.utils.ConnectionUtils.connectionMock;
import static org.dbunit.dataset.datatype.DataType.BIGINT;
import static org.dbunit.dataset.datatype.DataType.VARCHAR;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class DbUnitDataUtilsTest {

    private static final String NAME = "NAME";
    private static final Column NAME_COLUMN = new Column(NAME, VARCHAR);
    public static final String AGE = "AGE";
    private static final Column AGE_COLUMN = new Column(AGE, BIGINT);

    @Test
    public void testWith() {
        assertThat(with(NAME_COLUMN, "Jane")).isEqualToComparingFieldByField(new Field(NAME, "Jane"));
        assertThat(with(AGE_COLUMN, 25)).isEqualToComparingFieldByField(new Field(AGE, 25));
        assertThat(with(AGE_COLUMN, "25")).isEqualToComparingFieldByField(new Field(AGE, "25"));
        assertThat(withNull(AGE_COLUMN)).isEqualToComparingFieldByField(new Field(AGE, null));
    }

    @Test
    public void testCleanInsert() throws Exception {
        insert(CUSTOMERS, row().with(ID, 1).with(FIRST_NAME, "Jack").with(LAST_NAME, "Dou"),
                row().with(ID, 2).with(FIRST_NAME, "Peter").with(LAST_NAME, "Black"),
                row().with(ID, 3).with(FIRST_NAME, "Kris").with(LAST_NAME, "Nia"));
        Assertion.assertEquals(getCustomersTableFromXml(), getTable(CUSTOMERS));
    }

    @Test
    public void testClean() throws SQLException, DatabaseUnitException {
        Connection connection = connectionMock();
        IDatabaseConnection dbUnitConnection = new DatabaseConnection(connection);
        Statement stmt = mock(Statement.class);
        when(connection.createStatement()).thenReturn(stmt);
        clean(connectionAwareTable(CUSTOMERS, dbUnitConnection));

        InOrder inOrder = inOrder(stmt);
        inOrder.verify(stmt).execute("delete from CUSTOMERS");
        inOrder.verify(stmt).close();
    }

    @Test
    public void testCleanSeveralTables() throws SQLException, DatabaseUnitException {
        Connection connection = connectionMock();
        IDatabaseConnection dbUnitConnection = new DatabaseConnection(connection);
        Statement stmt = mock(Statement.class);
        when(connection.createStatement()).thenReturn(stmt);
        clean(connectionAwareTable(CUSTOMERS, dbUnitConnection), connectionAwareTable(ORDERS, dbUnitConnection));

        InOrder inOrder = inOrder(stmt);
        inOrder.verify(stmt).execute("delete from CUSTOMERS");
        inOrder.verify(stmt).close();
        inOrder.verify(stmt).execute("delete from ORDERS");
        inOrder.verify(stmt).close();
    }

    @Test
    public void testInsert() throws SQLException, DatabaseUnitException {
        Connection connection = connectionMock();
        PreparedStatement pstmt = mock(PreparedStatement.class);
        IDatabaseConnection dbUnitConnection = new DatabaseConnection(connection);
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);

        insert(connectionAwareTable(CUSTOMERS, dbUnitConnection),
                row().with(ID, 1).with(FIRST_NAME, "Kit").with(LAST_NAME, "Debito"));

        InOrder inOrder = inOrder(connection, pstmt);
        inOrder.verify(connection, times(1)).prepareStatement("insert into CUSTOMERS (ID, FIRST_NAME, LAST_NAME) values (?, ?, ?)");
        inOrder.verify(pstmt).setInt(1, 1);
        inOrder.verify(pstmt).setString(2, "Kit");
        inOrder.verify(pstmt).setString(3, "Debito");
        inOrder.verify(pstmt).execute();
        inOrder.verify(pstmt).close();
    }


    private ITable getCustomersTableFromXml() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/test/resources/customersSampleData.xml"))
                .getTable(CUSTOMERS.getName());
    }

}
