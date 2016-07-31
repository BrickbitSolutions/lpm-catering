package be.brickbit.lpm.catering.util;

import org.junit.Test;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;

public class DateUtilsTest {
    @Test
    public void getDateFormat() throws Exception {
        assertThat(DateUtils.getDateFormat()).isNotNull();
    }

    @Test
    public void getDateFormat__checkPattern() throws Exception {
        LocalDateTime time = LocalDateTime.of(1991, 5, 4, 23, 50, 40, 333);
        assertThat(DateUtils.getDateFormat().format(time)).isEqualTo("04-05-1991 23:50:40");
    }

    @Test
    public void calculateDifference() throws Exception {
        LocalDateTime start = LocalDateTime.of(1991, 5, 4, 23, 50, 40, 333);
        LocalDateTime end = LocalDateTime.of(1991, 5, 4, 23, 51, 0, 333);

        assertThat(DateUtils.calculateDifference(start, end)).isEqualTo(20);
    }
}