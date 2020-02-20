package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.ActionScript;

/**
 * Spring Data  repository for the ActionScript entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ActionScriptRepository extends JpaRepository<ActionScript, Long>, JpaSpecificationExecutor<ActionScript> {

}
