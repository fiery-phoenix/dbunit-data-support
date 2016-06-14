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

    @Test
    public void testWith() throws Exception {
        assertThat(with(new Column("NAME", VARCHAR), "Jane"))
                .isEqualToComparingFieldByField(new Field("NAME", "Jane"));
        assertThat(with(new Column("AGE", BIGINT), 25))
                .isEqualToComparingFieldByField(new Field("AGE", 25));
        assertThat(with(new Column("AGE", BIGINT), "25"))
                .isEqualToComparingFieldByField(new Field("AGE", "25"));
        assertThat(withNull(new Column("AGE", BIGINT)))
                .isEqualToComparingFieldByField(new Field("AGE", null));
    }

}
