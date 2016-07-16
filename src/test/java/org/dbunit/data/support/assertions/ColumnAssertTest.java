package org.dbunit.data.support.assertions;

import org.junit.Before;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.dbunit.data.support.DbUnitAssertions.assertThat;
import static org.dbunit.data.support.DbUnitDataUtils.deleteFrom;
import static org.dbunit.data.support.DbUnitDataUtils.insert;
import static org.dbunit.data.support.DbUnitDataUtils.row;
import static org.dbunit.data.support.generators.ValueGenerators.sequence;
import static org.dbunit.data.support.tables.tasks.Lists.COMMENT;
import static org.dbunit.data.support.tables.tasks.Lists.SUMMARY;
import static org.dbunit.data.support.tables.tasks.TasksTables.LISTS;
import static org.dbunit.data.support.tables.tasks.TasksTables.USERS;
import static org.dbunit.data.support.tables.tasks.Users.LOGIN;

public class ColumnAssertTest {

    @Before
    public void before() {
        deleteFrom(USERS, LISTS);
    }

    @Test
    public void test_hasNoNullValues() {
        insert(LISTS, row().times(5));
        assertThat(LISTS).column(SUMMARY).hasNoNullValues();
        assertThatThrownBy(() -> assertThat(LISTS).column(COMMENT).hasNoNullValues())
                .isInstanceOf(AssertionError.class);
    }

    @Test
    public void test_hasOnlyNullValues() {
        insert(LISTS, row().times(5));
        assertThat(LISTS).column(COMMENT).hasOnlyNullValues();
        assertThatThrownBy(() -> assertThat(LISTS).column(SUMMARY).hasOnlyNullValues())
                .isInstanceOf(AssertionError.class);
    }

    @Test
    public void test_hasValues() {
        insert(USERS, row().withGenerated(LOGIN, sequence("l")).times(3));
        assertThat(USERS).column(LOGIN).hasValues("l1", "l2", "l3");
        assertThatThrownBy(() -> assertThat(USERS).column(LOGIN).hasValues("l1", "l2"))
                .isInstanceOf(AssertionError.class);
        assertThatThrownBy(() -> assertThat(USERS).column(LOGIN).hasValues("l2", "l1", "l3"))
                .isInstanceOf(AssertionError.class);
    }

}
