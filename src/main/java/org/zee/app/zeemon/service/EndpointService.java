package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.Endpoint;
import org.zee.app.zeemon.repository.EndpointRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Endpoint}.
 */
@Service
@Transactional
public class EndpointService {

    private final Logger log = LoggerFactory.getLogger(EndpointService.class);

    private final EndpointRepository endpointRepository;

    public EndpointService(EndpointRepository endpointRepository) {
        this.endpointRepository = endpointRepository;
    }

    /**
     * Save a endpoint.
     *
     * @param endpoint the entity to save.
     * @return the persisted entity.
     */
    public Endpoint save(Endpoint endpoint) {
        log.debug("Request to save Endpoint : {}", endpoint);
        return endpointRepository.save(endpoint);
    }

    /**
     * Get all the endpoints.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Endpoint> findAll(Pageable pageable) {
        log.debug("Request to get all Endpoints");
        return endpointRepository.findAll(pageable);
    }

    /**
     * Get one endpoint by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Endpoint> findOne(Long id) {
        log.debug("Request to get Endpoint : {}", id);
        return endpointRepository.findById(id);
    }

    /**
     * Delete the endpoint by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Endpoint : {}", id);
        endpointRepository.deleteById(id);
    }
}
