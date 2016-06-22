package org.dbunit.data.support.model;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import static org.dbunit.data.support.DbUnitDataUtils.columns;
import static org.dbunit.data.support.tables.tasks.Users.ID;
import static org.dbunit.data.support.tables.tasks.Users.LOGIN;
import static org.dbunit.data.support.tables.tasks.Users.NAME;

public class RowsBuilderByColumnsTest {

    @Test
    public void fails_to_add_not_matching_number_of_values() {
        Assertions.assertThatThrownBy(() -> columns(ID, LOGIN, NAME)
                .values(1, "kit", "Sophi", "extra value")).isInstanceOf(IllegalArgumentException.class);
        Assertions.assertThatThrownBy(() -> columns(ID, LOGIN, NAME)
                .values(1, "kit")).isInstanceOf(IllegalArgumentException.class);
    }

}