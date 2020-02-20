package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;
import org.zee.app.zeemon.domain.EndpointProperty;

/**
 * Spring Data  repository for the EndpointProperty entity.
 */
@SuppressWarnings("unused")
@Repository
public interface EndpointPropertyRepository extends JpaRepository<EndpointProperty, Long>, JpaSpecificationExecutor<EndpointProperty> {

}
