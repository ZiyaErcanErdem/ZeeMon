package org.zee.app.zeemon.repository;

import java.time.Instant;
import java.util.List;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.Action;
import org.zee.app.zeemon.domain.enumeration.ActionState;

/**
 * Spring Data  repository for the Action entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionRepository extends JpaRepository<Action, Long>, JpaSpecificationExecutor<Action> {

	List<Action> findAllByActionStateAndNextExecutionStartTimeBetween(ActionState actionState, Instant lookupStartTime, Instant lookupEndTime);

}
