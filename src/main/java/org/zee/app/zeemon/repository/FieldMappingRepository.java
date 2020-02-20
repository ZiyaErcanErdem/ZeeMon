package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.FieldMapping;

/**
 * Spring Data  repository for the FieldMapping entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FieldMappingRepository extends JpaRepository<FieldMapping, Long>, JpaSpecificationExecutor<FieldMapping> {

}
