package org.dbunit.data.support.model;

import org.dbunit.DatabaseUnitException;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.junit.Test;

import java.io.File;

import static org.dbunit.Assertion.assertEquals;
import static org.dbunit.data.support.DbUnitDataUtils.columns;
import static org.dbunit.data.support.DbUnitDataUtils.row;
import static org.dbunit.data.support.generators.ValueGenerators.sequence;
import static org.dbunit.data.support.generators.ValueGenerators.stringSequence;
import static org.dbunit.data.support.tables.tasks.TasksTables.USERS;
import static org.dbunit.data.support.tables.tasks.Users.ID;
import static org.dbunit.data.support.tables.tasks.Users.LOGIN;
import static org.dbunit.data.support.tables.tasks.Users.NAME;

public class TableBuilderTest {

    @Test
    public void test_dataset_generation() throws Exception {
        assertEquals(getUsersDataSetFromXml(),
                new TableBuilder(
                        row().with(ID, 1).with(LOGIN, "kit").with(NAME, "Sophi"),
                        row().with(ID, 2).with(LOGIN, "gray").with(NAME, "Shellena"),
                        row().with(ID, 3).with(LOGIN, "pawel").with(NAME, "Pawel Dou")
                ).build(USERS));
    }

    @Test
    public void test_dataset_generation_for_columns_notation() throws Exception {
        assertEquals(getUsersDataSetFromXml(),
                new TableBuilder(columns(ID, LOGIN, NAME)
                        .values(1, "kit", "Sophi")
                        .values(2, "gray", "Shellena")
                        .values(3, "pawel", "Pawel Dou")
                ).build(USERS));
    }

    @Test
    public void absent_column_value_is_generated_when_applicable() throws DatabaseUnitException {
        IDataSet expectedDataSetWithAbsentColumn = new TableBuilder(row().with(ID, 1).with(LOGIN, "test1").with(NAME, "Test")).build(USERS);
        assertEquals(expectedDataSetWithAbsentColumn, new TableBuilder(row().with(ID, 1).with(LOGIN, "test1")).build(USERS));
        assertEquals(expectedDataSetWithAbsentColumn, new TableBuilder(columns(ID, LOGIN).values(1, "test1")).build(USERS));
    }

    @Test
    public void column_value_is_generated_when_applicable() throws DatabaseUnitException {
        assertEquals(new TableBuilder(
                        row().with(ID, 5).with(LOGIN, "login1"),
                        row().with(ID, 10).with(LOGIN, "login2")
                ).build(USERS),
                new TableBuilder(
                        row().withGenerated(ID, sequence(5, 5)).withGenerated(LOGIN, stringSequence("login")).times(2)
                ).build(USERS));
    }

    @Test
    public void column_value_for_template_row_is_generated_when_applicable() throws DatabaseUnitException {
        RowBuilder template = row().withGenerated(ID, sequence()).withGenerated(LOGIN, stringSequence("t"));
        assertEquals(new TableBuilder(
                        row().with(ID, 1).with(LOGIN, "t1"),
                        row().with(ID, 2).with(LOGIN, "other"),
                        row().with(ID, 3).with(LOGIN, "t2")
                ).build(USERS),
                new TableBuilder(
                        row(template),
                        row(template).with(LOGIN, "other"),
                        row(template)
                ).build(USERS));
    }

    private IDataSet getUsersDataSetFromXml() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File("src/test/resources/usersSampleData.xml"));
    }
}
