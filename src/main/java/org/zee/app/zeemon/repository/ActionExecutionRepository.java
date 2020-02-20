package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.ActionExecution;

/**
 * Spring Data  repository for the ActionExecution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionExecutionRepository extends JpaRepository<ActionExecution, Long>, JpaSpecificationExecutor<ActionExecution> {

}
