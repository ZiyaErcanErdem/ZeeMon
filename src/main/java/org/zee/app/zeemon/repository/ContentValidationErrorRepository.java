package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.ContentValidationError;

/**
 * Spring Data  repository for the ContentValidationError entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ContentValidationErrorRepository extends JpaRepository<ContentValidationError, Long>, JpaSpecificationExecutor<ContentValidationError> {

}
