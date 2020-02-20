package org.zee.app.zeemon.domain;

import org.junit.jupiter.api.Test;
import org.zee.app.zeemon.domain.EndpointProperty;

import static org.assertj.core.api.Assertions.assertThat;
import org.zee.app.zeemon.web.rest.TestUtil;

public class EndpointPropertyTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EndpointProperty.class);
        EndpointProperty endpointProperty1 = new EndpointProperty();
        endpointProperty1.setId(1L);
        EndpointProperty endpointProperty2 = new EndpointProperty();
        endpointProperty2.setId(endpointProperty1.getId());
        assertThat(endpointProperty1).isEqualTo(endpointProperty2);
        endpointProperty2.setId(2L);
        assertThat(endpointProperty1).isNotEqualTo(endpointProperty2);
        endpointProperty1.setId(null);
        assertThat(endpointProperty1).isNotEqualTo(endpointProperty2);
    }
}
