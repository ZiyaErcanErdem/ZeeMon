package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.ContentMapper;

/**
 * Spring Data  repository for the ContentMapper entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentMapperRepository extends JpaRepository<ContentMapper, Long>, JpaSpecificationExecutor<ContentMapper> {

}
