package org.dbunit.data.support.generators.random;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.dbunit.data.support.generators.ValueGenerators.any;

public class RandomIntValueGeneratorTest {

    @Test
    public void fails_to_create_generator_for_wrong_range() {
        assertThatThrownBy(() -> any().between(3, 1)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void next_value_is_generated_from_provided_range() {
        assertThat(any().between(1, 1).next()).isEqualTo(1);
        assertThat(any().between(1, 4).next()).isBetween(1, 4);
    }

}
