package org.zee.app.zeemon.repository;
import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.Flow;
import org.zee.app.zeemon.domain.enumeration.FlowState;


/**
 * Spring Data  repository for the Flow entity.
 */
@SuppressWarnings("unused")
@Repository
public interface FlowRepository extends JpaRepository<Flow, Long>, JpaSpecificationExecutor<Flow> {

	List<Flow> findAllByFlowStateAndNextExecutionStartTimeBetween(FlowState flowState, Instant executionTimeStart, Instant executionTimeEnd);
}
