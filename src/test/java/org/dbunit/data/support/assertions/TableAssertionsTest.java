package org.dbunit.data.support.assertions;

import org.dbunit.data.support.model.TableBuilder;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.dbunit.data.support.DbUnitAssertions.assertThat;
import static org.dbunit.data.support.DbUnitDataUtils.columns;
import static org.dbunit.data.support.DbUnitDataUtils.deleteFrom;
import static org.dbunit.data.support.DbUnitDataUtils.insert;
import static org.dbunit.data.support.DbUnitDataUtils.row;
import static org.dbunit.data.support.DbUnitDataUtils.table;
import static org.dbunit.data.support.tables.tasks.Packages.LISTS_LIMIT;
import static org.dbunit.data.support.tables.tasks.Packages.PRICE;
import static org.dbunit.data.support.tables.tasks.TasksTables.LISTS;
import static org.dbunit.data.support.tables.tasks.TasksTables.PACKAGES;
import static org.dbunit.data.support.tables.tasks.TasksTables.USERS;
import static org.dbunit.data.support.tables.tasks.Users.ID;
import static org.dbunit.data.support.tables.tasks.Users.LOGIN;

public class TableAssertionsTest {

    @Before
    public void before() {
        deleteFrom(USERS, LISTS);
    }

    @Test
    public void test_isEmpty() {
        assertThat(USERS).isEmpty();
    }

    @Test
    public void isEmpty_should_fail_when_table_has_data() {
        insert(USERS, row());
        assertThatThrownBy(() -> assertThat(USERS).isEmpty()).isInstanceOf(AssertionError.class);
    }

    @Test
    public void test_isEqualTo() {
        insert(USERS, columns(ID, LOGIN).values(1, "l1").values(2, "l2"));
        assertThat(USERS).isEqualTo(table(
                row().with(ID, 1).with(LOGIN, "l1"),
                row().with(ID, 2).with(LOGIN, "l2")));
    }

    @Test
    public void isEqualTo_should_fail_for_different_rows_order() {
        insert(USERS, columns(ID, LOGIN).values(1, "l1").values(2, "l2"));
        assertThatThrownBy(() -> assertThat(USERS).isEqualTo(table(
                row().with(ID, 2).with(LOGIN, "l2"),
                row().with(ID, 1).with(LOGIN, "l1")
        ))).isInstanceOf(AssertionError.class);
    }

    @Test
    public void test_isEqualTo_ignoring_columns() {
        insert(USERS, columns(LOGIN).values("l1").values("l2"));
        TableBuilder expectedTable = table(
                row().with(LOGIN, "l1"),
                row().with(LOGIN, "l2")
        );
        assertThat(USERS).ignoring(ID).isEqualTo(expectedTable);
        assertThatThrownBy(() -> assertThat(USERS).isEqualTo(expectedTable))
                .isInstanceOf(AssertionError.class);
    }

    @Test
    public void test_isEqualTo_with_numeric() {
        insert(PACKAGES, columns(LISTS_LIMIT, PRICE).values(5, 25.5000000009).values(10, 50));
        TableBuilder expectedTable = table(
                row().with(LISTS_LIMIT, 5).with(PRICE, new BigDecimal("25.5")),
                row().with(LISTS_LIMIT, 10).with(PRICE, "50")
        );
        assertThat(PACKAGES).ignoring("ID", "SUMMARY").isEqualTo(expectedTable);
    }

    @Test
    public void test_isEqualTo_ignoring_order() {
        insert(USERS, columns(ID, LOGIN).values(1, "l1").values(2, "l2"));
        assertThat(USERS).ignoringOrder().isEqualTo(table(
                row().with(ID, 2).with(LOGIN, "l2"),
                row().with(ID, 1).with(LOGIN, "l1")
        ));
    }

}
