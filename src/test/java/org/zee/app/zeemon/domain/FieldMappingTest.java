package org.zee.app.zeemon.domain;

import org.junit.jupiter.api.Test;
import org.zee.app.zeemon.domain.FieldMapping;

import static org.assertj.core.api.Assertions.assertThat;
import org.zee.app.zeemon.web.rest.TestUtil;

public class FieldMappingTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(FieldMapping.class);
        FieldMapping fieldMapping1 = new FieldMapping();
        fieldMapping1.setId(1L);
        FieldMapping fieldMapping2 = new FieldMapping();
        fieldMapping2.setId(fieldMapping1.getId());
        assertThat(fieldMapping1).isEqualTo(fieldMapping2);
        fieldMapping2.setId(2L);
        assertThat(fieldMapping1).isNotEqualTo(fieldMapping2);
        fieldMapping1.setId(null);
        assertThat(fieldMapping1).isNotEqualTo(fieldMapping2);
    }
}
