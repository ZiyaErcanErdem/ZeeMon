package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.ActionParam;

/**
 * Spring Data  repository for the ActionParam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionParamRepository extends JpaRepository<ActionParam, Long>, JpaSpecificationExecutor<ActionParam> {

}
