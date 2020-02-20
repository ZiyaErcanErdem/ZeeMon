package org.zee.app.zeemon.domain;

import org.junit.jupiter.api.Test;
import org.zee.app.zeemon.domain.CheckScript;

import static org.assertj.core.api.Assertions.assertThat;
import org.zee.app.zeemon.web.rest.TestUtil;

public class CheckScriptTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(CheckScript.class);
        CheckScript checkScript1 = new CheckScript();
        checkScript1.setId(1L);
        CheckScript checkScript2 = new CheckScript();
        checkScript2.setId(checkScript1.getId());
        assertThat(checkScript1).isEqualTo(checkScript2);
        checkScript2.setId(2L);
        assertThat(checkScript1).isNotEqualTo(checkScript2);
        checkScript1.setId(null);
        assertThat(checkScript1).isNotEqualTo(checkScript2);
    }
}
