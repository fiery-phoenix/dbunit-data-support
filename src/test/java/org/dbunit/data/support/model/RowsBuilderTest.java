package org.dbunit.data.support.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dbunit.data.support.DbUnitDataUtils.row;
import static org.dbunit.data.support.tables.tasks.Users.ID;
import static org.dbunit.data.support.tables.tasks.Users.LOGIN;
import static org.dbunit.data.support.tables.tasks.Users.NAME;

public class RowsBuilderTest {

    @Test
    public void template_row_works_and_stays_unchanged() {
        RowsBuilder templateRow = row().with(ID, 1).with(NAME, "Test");

        assertThat(row(templateRow).with(LOGIN, "test")).isEqualTo(row().with(ID, 1).with(LOGIN, "test").with(NAME, "Test"));
        assertThat(row(templateRow).with(ID, 2).with(LOGIN, "test")).isEqualTo(row().with(ID, 2).with(LOGIN, "test").with(NAME, "Test"));
        assertThat(templateRow).isEqualTo(row().with(ID, 1).with(NAME, "Test"));
    }

}
