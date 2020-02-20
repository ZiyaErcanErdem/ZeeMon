package org.zee.app.zeemon.domain;

import org.junit.jupiter.api.Test;
import org.zee.app.zeemon.domain.ActionExecution;

import static org.assertj.core.api.Assertions.assertThat;
import org.zee.app.zeemon.web.rest.TestUtil;

public class ActionExecutionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionExecution.class);
        ActionExecution actionExecution1 = new ActionExecution();
        actionExecution1.setId(1L);
        ActionExecution actionExecution2 = new ActionExecution();
        actionExecution2.setId(actionExecution1.getId());
        assertThat(actionExecution1).isEqualTo(actionExecution2);
        actionExecution2.setId(2L);
        assertThat(actionExecution1).isNotEqualTo(actionExecution2);
        actionExecution1.setId(null);
        assertThat(actionExecution1).isNotEqualTo(actionExecution2);
    }
}
