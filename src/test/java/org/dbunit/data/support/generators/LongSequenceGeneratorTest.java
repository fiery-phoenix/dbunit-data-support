package org.dbunit.data.support.generators;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.dbunit.data.support.generators.ValueGenerators.sequence;

public class LongSequenceGeneratorTest {

    @Test
    public void by_default_sequence_starts_at_one_and_is_incremented_by_one() {
        LongSequenceGenerator sequence = sequence();
        assertThat(sequence.next()).isEqualTo(1);
        assertThat(sequence.next()).isEqualTo(2);
    }

    @Test
    public void sequence_starts_at_passed_value_and_is_incremented_accordingly() {
        LongSequenceGenerator sequence = sequence(5, 7);
        assertThat(sequence.next()).isEqualTo(5);
        assertThat(sequence.next()).isEqualTo(12);
    }

    @Test
    public void resetting_sequence_start_value_works_correctly() {
        LongSequenceGenerator sequence = sequence(5, 2);
        LongSequenceGenerator restartedSequence = sequence.startAt(1);
        assertThat(sequence.next()).isEqualTo(5);
        assertThat(restartedSequence.next()).isEqualTo(1);
    }

    @Test
    public void resetting_sequence_step_works_correctly() {
        LongSequenceGenerator sequence = sequence();
        LongSequenceGenerator sequenceWithNewStep = sequence.incrementingBy(4);
        assertThat(sequence.next()).isEqualTo(1);
        assertThat(sequence.next()).isEqualTo(2);
        assertThat(sequenceWithNewStep.next()).isEqualTo(1);
        assertThat(sequenceWithNewStep.next()).isEqualTo(5);
    }

}
