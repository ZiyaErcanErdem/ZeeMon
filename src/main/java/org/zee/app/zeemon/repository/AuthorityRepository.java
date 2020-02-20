package org.zee.app.zeemon.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.zee.app.zeemon.domain.Authority;

/**
 * Spring Data JPA repository for the {@link Authority} entity.
 */
public interface AuthorityRepository extends JpaRepository<Authority, String> {
}
