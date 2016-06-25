package org.dbunit.data.support.generators;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ValueGeneratorsTest {

    @Test
    public void constant_value_generator_returns_passed_constant() throws Exception {
        ValueGenerator<String> stringConstantGenerator = ValueGenerators.constant("test");
        assertThat(stringConstantGenerator.next()).isEqualTo("test");
        assertThat(stringConstantGenerator.next()).isEqualTo("test");

        ValueGenerator<Integer> intConstantGenerator = ValueGenerators.constant(5);
        assertThat(intConstantGenerator.next()).isEqualTo(5);
        assertThat(intConstantGenerator.next()).isEqualTo(5);
    }
}
