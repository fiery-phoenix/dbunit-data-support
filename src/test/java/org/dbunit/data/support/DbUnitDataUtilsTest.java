package org.dbunit.data.support;

import org.dbunit.data.support.model.Field;
import org.dbunit.dataset.Column;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dbunit.data.support.DbUnitDataUtils.with;
import static org.dbunit.data.support.DbUnitDataUtils.withNull;
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

}
