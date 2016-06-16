package org.dbunit.data.support.model;

import org.dbunit.Assertion;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import java.io.File;

import static org.dbunit.data.support.DbUnitDataUtils.row;
import static org.dbunit.data.support.DbUnitDataUtils.with;
import static org.dbunit.data.support.tables.Customers.FIRST_NAME;
import static org.dbunit.data.support.tables.Customers.ID;
import static org.dbunit.data.support.tables.Customers.LAST_NAME;
import static org.dbunit.data.support.tables.SampleTables.CUSTOMERS;

public class TableBuilderTest {

    @Test
    public void testBuild() throws Exception {
        Assertion.assertEquals(getCustomersDataSetFromXml(),
                new TableBuilder(
                        row(with(ID, 1), with(FIRST_NAME, "Jack"), with(LAST_NAME, "Dou")),
                        row(with(ID, 2), with(FIRST_NAME, "Peter"), with(LAST_NAME, "Black")),
                        row(with(ID, 3), with(FIRST_NAME, "Kris"), with(LAST_NAME, "Nia"))
                ).build(CUSTOMERS));
    }

    private IDataSet getCustomersDataSetFromXml() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/test/resources/customersSampleData.xml"));
    }
}
