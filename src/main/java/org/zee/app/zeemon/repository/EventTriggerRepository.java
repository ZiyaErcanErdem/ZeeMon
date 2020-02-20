package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.EventTrigger;

/**
 * Spring Data  repository for the EventTrigger entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EventTriggerRepository extends JpaRepository<EventTrigger, Long>, JpaSpecificationExecutor<EventTrigger> {

}
