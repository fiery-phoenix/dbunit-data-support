package org.dbunit.data.support.generators.random;

import org.junit.Test;

import static java.util.Arrays.asList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.dbunit.data.support.generators.ValueGenerators.any;

public class RandomValueGeneratorTest {

    @Test
    public void fails_to_create_random_value_generator_without_values() {
        assertThatThrownBy(() -> any().of()).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void next_value_is_generated_from_provided_values() {
        assertThat(any().of(1).next()).isEqualTo(1);
        assertThat(any().of(1, 2, 3, 4).next()).isBetween(1, 4);
        assertThat(any().of(asList(1, 2, 3, 4)).next()).isBetween(1, 4);
    }

}
