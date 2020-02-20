package org.zee.app.zeemon.domain;

import org.junit.jupiter.api.Test;
import org.zee.app.zeemon.domain.TaskExecution;

import static org.assertj.core.api.Assertions.assertThat;
import org.zee.app.zeemon.web.rest.TestUtil;

public class TaskExecutionTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(TaskExecution.class);
        TaskExecution taskExecution1 = new TaskExecution();
        taskExecution1.setId(1L);
        TaskExecution taskExecution2 = new TaskExecution();
        taskExecution2.setId(taskExecution1.getId());
        assertThat(taskExecution1).isEqualTo(taskExecution2);
        taskExecution2.setId(2L);
        assertThat(taskExecution1).isNotEqualTo(taskExecution2);
        taskExecution1.setId(null);
        assertThat(taskExecution1).isNotEqualTo(taskExecution2);
    }
}
