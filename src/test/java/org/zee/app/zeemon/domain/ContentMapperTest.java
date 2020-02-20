package org.zee.app.zeemon.domain;

import org.junit.jupiter.api.Test;
import org.zee.app.zeemon.domain.ContentMapper;

import static org.assertj.core.api.Assertions.assertThat;
import org.zee.app.zeemon.web.rest.TestUtil;

public class ContentMapperTest {

    @Test
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ContentMapper.class);
        ContentMapper contentMapper1 = new ContentMapper();
        contentMapper1.setId(1L);
        ContentMapper contentMapper2 = new ContentMapper();
        contentMapper2.setId(contentMapper1.getId());
        assertThat(contentMapper1).isEqualTo(contentMapper2);
        contentMapper2.setId(2L);
        assertThat(contentMapper1).isNotEqualTo(contentMapper2);
        contentMapper1.setId(null);
        assertThat(contentMapper1).isNotEqualTo(contentMapper2);
    }
}
