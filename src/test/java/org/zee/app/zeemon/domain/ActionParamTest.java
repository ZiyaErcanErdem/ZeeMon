package org.zee.app.zeemon.domain;

import org.junit.jupiter.api.Test;
import org.zee.app.zeemon.domain.ActionParam;

import static org.assertj.core.api.Assertions.assertThat;
import org.zee.app.zeemon.web.rest.TestUtil;

public class ActionParamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionParam.class);
        ActionParam actionParam1 = new ActionParam();
        actionParam1.setId(1L);
        ActionParam actionParam2 = new ActionParam();
        actionParam2.setId(actionParam1.getId());
        assertThat(actionParam1).isEqualTo(actionParam2);
        actionParam2.setId(2L);
        assertThat(actionParam1).isNotEqualTo(actionParam2);
        actionParam1.setId(null);
        assertThat(actionParam1).isNotEqualTo(actionParam2);
    }
}
