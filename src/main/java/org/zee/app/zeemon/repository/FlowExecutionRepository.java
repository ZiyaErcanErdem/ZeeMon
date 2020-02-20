package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.FlowExecution;

/**
 * Spring Data  repository for the FlowExecution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FlowExecutionRepository extends JpaRepository<FlowExecution, Long>, JpaSpecificationExecutor<FlowExecution> {

}
