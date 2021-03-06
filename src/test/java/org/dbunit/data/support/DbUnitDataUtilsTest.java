package org.dbunit.data.support;

import org.dbunit.DatabaseUnitException;
import org.dbunit.database.DatabaseConnection;
import org.dbunit.database.IDatabaseConnection;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;
import org.mockito.InOrder;

import java.io.File;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import static java.math.BigDecimal.ONE;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.dbunit.data.support.DbUnitAssertions.assertThat;
import static org.dbunit.data.support.DbUnitDataUtils.cleanInsert;
import static org.dbunit.data.support.DbUnitDataUtils.columns;
import static org.dbunit.data.support.DbUnitDataUtils.deleteFrom;
import static org.dbunit.data.support.DbUnitDataUtils.insert;
import static org.dbunit.data.support.DbUnitDataUtils.row;
import static org.dbunit.data.support.tables.tasks.TasksTables.LISTS;
import static org.dbunit.data.support.tables.tasks.TasksTables.USERS;
import static org.dbunit.data.support.tables.tasks.Users.ID;
import static org.dbunit.data.support.tables.tasks.Users.LOGIN;
import static org.dbunit.data.support.tables.tasks.Users.NAME;
import static org.dbunit.data.support.utils.ConnectionUtils.connectionAwareTable;
import static org.dbunit.data.support.utils.ConnectionUtils.connectionMock;
import static org.mockito.Matchers.anyString;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

public class DbUnitDataUtilsTest {

    @Test
    public void test_cleanInsert() throws Exception {
        cleanInsert(USERS, row().with(ID, 1).with(LOGIN, "kit").with(NAME, "Sophi"),
                row().with(ID, 2).with(LOGIN, "gray").with(NAME, "Shellena"),
                row().with(ID, 3).with(LOGIN, "pawel").with(NAME, "Pawel Dou"));
        assertThat(USERS).isEqualTo(getUsersTableFromXml());
    }

    @Test
    public void test_cleanInsert_with_repeating_row() throws Exception {
        cleanInsert(USERS, row().with(NAME, "Shellena").times(4));
        assertThat(USERS).hasSize(4);
    }

    @Test
    public void test_cleanInsert_with_repeating_columns_values() throws Exception {
        cleanInsert(USERS, columns(NAME).repeatingValues("Shellena").times(4));
        assertThat(USERS).hasSize(4);
    }

    @Test
    public void test_cleanInsert_for_columns_notation() throws Exception {
        cleanInsert(USERS, columns(ID, LOGIN, NAME)
                .values(1, "kit", "Sophi")
                .values(2, "gray", "Shellena")
                .values(3, "pawel", "Pawel Dou"));
        assertThat(USERS).isEqualTo(getUsersTableFromXml());
    }

    @Test
    public void test_clean() throws SQLException, DatabaseUnitException {
        Connection connection = connectionMock();
        IDatabaseConnection dbUnitConnection = new DatabaseConnection(connection);
        Statement stmt = mock(Statement.class);
        when(connection.createStatement()).thenReturn(stmt);
        deleteFrom(connectionAwareTable(USERS, dbUnitConnection));

        InOrder inOrder = inOrder(stmt);
        inOrder.verify(stmt).execute("delete from USERS");
        inOrder.verify(stmt).close();
    }

    @Test
    public void test_deleteFrom_for_several_tables() throws SQLException, DatabaseUnitException {
        Connection connection = connectionMock();
        IDatabaseConnection dbUnitConnection = new DatabaseConnection(connection);
        Statement stmt = mock(Statement.class);
        when(connection.createStatement()).thenReturn(stmt);
        DbUnitDataUtils.deleteFrom(connectionAwareTable(USERS, dbUnitConnection), connectionAwareTable(LISTS, dbUnitConnection));

        InOrder inOrder = inOrder(stmt);
        inOrder.verify(stmt).execute("delete from USERS");
        inOrder.verify(stmt).close();
        inOrder.verify(stmt).execute("delete from LISTS");
        inOrder.verify(stmt).close();
    }

    @Test
    public void test_insert() throws SQLException, DatabaseUnitException {
        Connection connection = connectionMock();
        PreparedStatement pstmt = mock(PreparedStatement.class);
        IDatabaseConnection dbUnitConnection = new DatabaseConnection(connection);
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);

        insert(connectionAwareTable(USERS, dbUnitConnection),
                row().with(ID, 1).with(LOGIN, "kit").with(NAME, "Sophi"));

        InOrder inOrder = inOrder(connection, pstmt);
        inOrder.verify(connection, times(1)).prepareStatement("insert into USERS (ID, LOGIN, NAME) values (?, ?, ?)");
        inOrder.verify(pstmt).setBigDecimal(1, ONE);
        inOrder.verify(pstmt).setString(2, "kit");
        inOrder.verify(pstmt).setString(3, "Sophi");
        inOrder.verify(pstmt).execute();
        inOrder.verify(pstmt).close();
    }

    @Test
    public void test_insert_with_columns_notation() throws SQLException, DatabaseUnitException {
        Connection connection = connectionMock();
        PreparedStatement pstmt = mock(PreparedStatement.class);
        IDatabaseConnection dbUnitConnection = new DatabaseConnection(connection);
        when(connection.prepareStatement(anyString())).thenReturn(pstmt);

        insert(connectionAwareTable(USERS, dbUnitConnection),
                columns(ID, LOGIN, NAME).values(1, "kit", "Sophi"));

        InOrder inOrder = inOrder(connection, pstmt);
        inOrder.verify(connection, times(1)).prepareStatement("insert into USERS (ID, LOGIN, NAME) values (?, ?, ?)");
        inOrder.verify(pstmt).setBigDecimal(1, ONE);
        inOrder.verify(pstmt).setString(2, "kit");
        inOrder.verify(pstmt).setString(3, "Sophi");
        inOrder.verify(pstmt).execute();
        inOrder.verify(pstmt).close();
    }

    @Test
    public void not_nullable_generatable_columns_are_inserted() {
        cleanInsert(USERS, row().times(4));
        assertThat(USERS).hasSize(4);

        assertThatThrownBy(() -> cleanInsert(USERS, row().withNull(LOGIN))).hasCauseInstanceOf(DatabaseUnitException.class);
        assertThatThrownBy(() -> cleanInsert(USERS, row().with(LOGIN, "test").times(2)))
                .hasCauseInstanceOf(DatabaseUnitException.class);
    }

    private ITable getUsersTableFromXml() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/test/resources/usersSampleData.xml"))
                .getTable(USERS.getName());
    }

}
