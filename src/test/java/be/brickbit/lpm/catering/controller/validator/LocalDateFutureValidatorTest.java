package be.brickbit.lpm.catering.controller.validator;

import org.junit.Test;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateFutureValidatorTest {
    private LocalDateFutureValidator validator = new LocalDateFutureValidator();

    @Test
    public void refusesDateInThePast() throws Exception {
        assertThat(validator.isValid(LocalDate.now().minusDays(1), null)).isFalse();
    }

    @Test
    public void refusesDateNow() throws Exception {
        assertThat(validator.isValid(LocalDate.now(), null)).isFalse();
    }

    @Test
    public void acceptsDateInFuture() throws Exception {
        assertThat(validator.isValid(LocalDate.now().plusDays(1), null)).isTrue();
    }

    @Test
    public void acceptsNullValues() throws Exception {
        assertThat(validator.isValid(null, null)).isTrue();
    }

}