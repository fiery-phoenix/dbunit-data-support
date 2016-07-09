package org.dbunit.data.support.generators;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.dbunit.data.support.generators.ValueGenerators.sequence;

public class StringSequenceGeneratorTest {

    @Test
    public void string_sequence_prefix_cannot_be_null() {
        assertThatThrownBy(() -> sequence(null)).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void by_default_sequence_starts_at_one_and_is_incremented_by_one() {
        StringSequenceGenerator sequence = sequence("test");
        assertThat(sequence.next()).isEqualTo("test1");
        assertThat(sequence.next()).isEqualTo("test2");
    }

    @Test
    public void sequence_starts_at_passed_value_and_is_incremented_accordingly() {
        StringSequenceGenerator sequence = sequence("test", 5, 7);
        assertThat(sequence.next()).isEqualTo("test5");
        assertThat(sequence.next()).isEqualTo("test12");
    }

    @Test
    public void resetting_sequence_start_value_works_correctly() {
        StringSequenceGenerator sequence = sequence("test", 5, 2);
        StringSequenceGenerator restartedSequence = sequence.startAt(1);
        assertThat(sequence.next()).isEqualTo("test5");
        assertThat(restartedSequence.next()).isEqualTo("test1");
    }

    @Test
    public void resetting_sequence_step_works_correctly() {
        StringSequenceGenerator sequence = sequence("test");
        StringSequenceGenerator sequenceWithNewStep = sequence.incrementingBy(4);
        assertThat(sequence.next()).isEqualTo("test1");
        assertThat(sequence.next()).isEqualTo("test2");
        assertThat(sequenceWithNewStep.next()).isEqualTo("test1");
        assertThat(sequenceWithNewStep.next()).isEqualTo("test5");
    }

}