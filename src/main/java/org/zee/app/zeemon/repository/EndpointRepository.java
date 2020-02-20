package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.Endpoint;

/**
 * Spring Data  repository for the Endpoint entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndpointRepository extends JpaRepository<Endpoint, Long>, JpaSpecificationExecutor<Endpoint> {

}
