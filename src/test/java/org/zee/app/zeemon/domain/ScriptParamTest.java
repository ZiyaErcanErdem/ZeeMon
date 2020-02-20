package org.zee.app.zeemon.domain;

import org.junit.jupiter.api.Test;
import org.zee.app.zeemon.domain.ScriptParam;

import static org.assertj.core.api.Assertions.assertThat;
import org.zee.app.zeemon.web.rest.TestUtil;

public class ScriptParamTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ScriptParam.class);
        ScriptParam scriptParam1 = new ScriptParam();
        scriptParam1.setId(1L);
        ScriptParam scriptParam2 = new ScriptParam();
        scriptParam2.setId(scriptParam1.getId());
        assertThat(scriptParam1).isEqualTo(scriptParam2);
        scriptParam2.setId(2L);
        assertThat(scriptParam1).isNotEqualTo(scriptParam2);
        scriptParam1.setId(null);
        assertThat(scriptParam1).isNotEqualTo(scriptParam2);
    }
}
