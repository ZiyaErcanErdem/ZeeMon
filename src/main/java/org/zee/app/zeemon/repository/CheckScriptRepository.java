package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.CheckScript;

/**
 * Spring Data  repository for the CheckScript entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckScriptRepository extends JpaRepository<CheckScript, Long>, JpaSpecificationExecutor<CheckScript> {

}
