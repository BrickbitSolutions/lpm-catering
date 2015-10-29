package be.brickbit.lpm.catering.domain;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class ClearanceTypeTest {

    @Test
    public void testFrom() throws Exception {
        assertThat(ClearanceType.from(0)).isEqualTo(ClearanceType.ANY);
    }

    @Test(expected = RuntimeException.class)
    public void testFromFail() throws Exception{
        ClearanceType.from(33);
    }
}