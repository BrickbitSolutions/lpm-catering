package be.brickbit.lpm.catering.util;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class DateUtilsTest {
    @Test
    public void getDateFormat() throws Exception {
        assertThat(DateUtils.getDateFormat()).isNotNull();
    }

}