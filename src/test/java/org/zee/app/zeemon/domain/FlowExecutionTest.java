package org.zee.app.zeemon.domain;

import org.junit.jupiter.api.Test;
import org.zee.app.zeemon.domain.FlowExecution;

import static org.assertj.core.api.Assertions.assertThat;
import org.zee.app.zeemon.web.rest.TestUtil;

public class FlowExecutionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FlowExecution.class);
        FlowExecution flowExecution1 = new FlowExecution();
        flowExecution1.setId(1L);
        FlowExecution flowExecution2 = new FlowExecution();
        flowExecution2.setId(flowExecution1.getId());
        assertThat(flowExecution1).isEqualTo(flowExecution2);
        flowExecution2.setId(2L);
        assertThat(flowExecution1).isNotEqualTo(flowExecution2);
        flowExecution1.setId(null);
        assertThat(flowExecution1).isNotEqualTo(flowExecution2);
    }
}
