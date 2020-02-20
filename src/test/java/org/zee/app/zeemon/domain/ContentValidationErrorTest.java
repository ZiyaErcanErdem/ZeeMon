package org.zee.app.zeemon.domain;

import org.junit.jupiter.api.Test;
import org.zee.app.zeemon.domain.ContentValidationError;

import static org.assertj.core.api.Assertions.assertThat;
import org.zee.app.zeemon.web.rest.TestUtil;

public class ContentValidationErrorTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentValidationError.class);
        ContentValidationError contentValidationError1 = new ContentValidationError();
        contentValidationError1.setId(1L);
        ContentValidationError contentValidationError2 = new ContentValidationError();
        contentValidationError2.setId(contentValidationError1.getId());
        assertThat(contentValidationError1).isEqualTo(contentValidationError2);
        contentValidationError2.setId(2L);
        assertThat(contentValidationError1).isNotEqualTo(contentValidationError2);
        contentValidationError1.setId(null);
        assertThat(contentValidationError1).isNotEqualTo(contentValidationError2);
    }
}
