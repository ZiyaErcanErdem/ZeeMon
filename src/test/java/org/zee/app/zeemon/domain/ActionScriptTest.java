package org.zee.app.zeemon.domain;

import org.junit.jupiter.api.Test;
import org.zee.app.zeemon.domain.ActionScript;

import static org.assertj.core.api.Assertions.assertThat;
import org.zee.app.zeemon.web.rest.TestUtil;

public class ActionScriptTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ActionScript.class);
        ActionScript actionScript1 = new ActionScript();
        actionScript1.setId(1L);
        ActionScript actionScript2 = new ActionScript();
        actionScript2.setId(actionScript1.getId());
        assertThat(actionScript1).isEqualTo(actionScript2);
        actionScript2.setId(2L);
        assertThat(actionScript1).isNotEqualTo(actionScript2);
        actionScript1.setId(null);
        assertThat(actionScript1).isNotEqualTo(actionScript2);
    }
}
