package org.zee.app.zeemon.domain;

import org.junit.jupiter.api.Test;
import org.zee.app.zeemon.domain.EventTrigger;

import static org.assertj.core.api.Assertions.assertThat;
import org.zee.app.zeemon.web.rest.TestUtil;

public class EventTriggerTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(EventTrigger.class);
        EventTrigger eventTrigger1 = new EventTrigger();
        eventTrigger1.setId(1L);
        EventTrigger eventTrigger2 = new EventTrigger();
        eventTrigger2.setId(eventTrigger1.getId());
        assertThat(eventTrigger1).isEqualTo(eventTrigger2);
        eventTrigger2.setId(2L);
        assertThat(eventTrigger1).isNotEqualTo(eventTrigger2);
        eventTrigger1.setId(null);
        assertThat(eventTrigger1).isNotEqualTo(eventTrigger2);
    }
}
