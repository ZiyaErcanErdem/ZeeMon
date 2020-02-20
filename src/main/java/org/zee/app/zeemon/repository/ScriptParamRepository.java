package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.ScriptParam;

/**
 * Spring Data  repository for the ScriptParam entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ScriptParamRepository extends JpaRepository<ScriptParam, Long>, JpaSpecificationExecutor<ScriptParam> {

}
