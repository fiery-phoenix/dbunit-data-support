package org.dbunit.data.support;

import org.dbunit.Assertion;
import org.dbunit.data.support.model.Field;
import org.dbunit.dataset.Column;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import java.io.File;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dbunit.data.support.DbUnitDataUtils.cleanInsert;
import static org.dbunit.data.support.DbUnitDataUtils.row;
import static org.dbunit.data.support.DbUnitDataUtils.getTable;
import static org.dbunit.data.support.DbUnitDataUtils.with;
import static org.dbunit.data.support.DbUnitDataUtils.withNull;
import static org.dbunit.data.support.tables.Customers.FIRST_NAME;
import static org.dbunit.data.support.tables.Customers.ID;
import static org.dbunit.data.support.tables.Customers.LAST_NAME;
import static org.dbunit.data.support.tables.SampleTables.CUSTOMERS;
import static org.dbunit.dataset.datatype.DataType.BIGINT;
import static org.dbunit.dataset.datatype.DataType.VARCHAR;

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
        cleanInsert(CUSTOMERS, row(with(ID, 1), with(FIRST_NAME, "Jack"), with(LAST_NAME, "Dou")),
                row(with(ID, 2), with(FIRST_NAME, "Peter"), with(LAST_NAME, "Black")),
                row(with(ID, 3), with(FIRST_NAME, "Kris"), with(LAST_NAME, "Nia")));
        Assertion.assertEquals(getCustomersTableFromXml(), getTable(CUSTOMERS));
    }

    private ITable getCustomersTableFromXml() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/test/resources/customersSampleData.xml"))
                .getTable(CUSTOMERS.getName());
    }

}
