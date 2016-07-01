package org.dbunit.data.support.model;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.dbunit.data.support.DbUnitDataUtils.columns;
import static org.dbunit.data.support.generators.ValueGenerators.sequence;
import static org.dbunit.data.support.tables.tasks.Users.ID;
import static org.dbunit.data.support.tables.tasks.Users.LOGIN;
import static org.dbunit.data.support.tables.tasks.Users.NAME;

public class RowsBuilderByColumnsTest {

    @Test
    public void fails_to_add_not_matching_number_of_values() {
        assertThatThrownBy(() -> columns(ID, LOGIN, NAME)
                .values(1, "kit", "Sophi", "extra value")).isInstanceOf(IllegalArgumentException.class);
        assertThatThrownBy(() -> columns(ID, LOGIN, NAME)
                .values(1, "kit")).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void rows_can_be_constructed_with_string_columns_names() {
        assertThat(columns("ID", "LOGIN", "NAME").values(1, "test", "Test"))
                .isEqualTo(columns(ID, LOGIN, NAME).values(1, "test", "Test"));
    }

    @Test
    public void fails_to_add_generatable_column_for_already_mentioned_column() {
        assertThatThrownBy(() -> columns(ID, LOGIN, NAME).values(1, "test", "Test").withGenerated(ID, sequence()))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Column ID is already present in the set of columns' names");
    }
}
