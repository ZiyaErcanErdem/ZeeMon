package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.TaskExecution;

/**
 * Spring Data  repository for the TaskExecution entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TaskExecutionRepository extends JpaRepository<TaskExecution, Long>, JpaSpecificationExecutor<TaskExecution> {

}
