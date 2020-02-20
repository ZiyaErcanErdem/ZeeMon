package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.EndpointProperty;
import org.zee.app.zeemon.repository.EndpointPropertyRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EndpointProperty}.
 */
@Service
@Transactional
public class EndpointPropertyService {

    private final Logger log = LoggerFactory.getLogger(EndpointPropertyService.class);

    private final EndpointPropertyRepository endpointPropertyRepository;

    public EndpointPropertyService(EndpointPropertyRepository endpointPropertyRepository) {
        this.endpointPropertyRepository = endpointPropertyRepository;
    }

    /**
     * Save a endpointProperty.
     *
     * @param endpointProperty the entity to save.
     * @return the persisted entity.
     */
    public EndpointProperty save(EndpointProperty endpointProperty) {
        log.debug("Request to save EndpointProperty : {}", endpointProperty);
        return endpointPropertyRepository.save(endpointProperty);
    }

    /**
     * Get all the endpointProperties.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EndpointProperty> findAll(Pageable pageable) {
        log.debug("Request to get all EndpointProperties");
        return endpointPropertyRepository.findAll(pageable);
    }

    /**
     * Get one endpointProperty by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EndpointProperty> findOne(Long id) {
        log.debug("Request to get EndpointProperty : {}", id);
        return endpointPropertyRepository.findById(id);
    }

    /**
     * Delete the endpointProperty by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EndpointProperty : {}", id);
        endpointPropertyRepository.deleteById(id);
    }
}
