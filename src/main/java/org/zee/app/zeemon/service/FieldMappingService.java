package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.FieldMapping;
import org.zee.app.zeemon.repository.FieldMappingRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FieldMapping}.
 */
@Service
@Transactional
public class FieldMappingService {

    private final Logger log = LoggerFactory.getLogger(FieldMappingService.class);

    private final FieldMappingRepository fieldMappingRepository;

    public FieldMappingService(FieldMappingRepository fieldMappingRepository) {
        this.fieldMappingRepository = fieldMappingRepository;
    }

    /**
     * Save a fieldMapping.
     *
     * @param fieldMapping the entity to save.
     * @return the persisted entity.
     */
    public FieldMapping save(FieldMapping fieldMapping) {
        log.debug("Request to save FieldMapping : {}", fieldMapping);
        return fieldMappingRepository.save(fieldMapping);
    }

    /**
     * Get all the fieldMappings.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FieldMapping> findAll(Pageable pageable) {
        log.debug("Request to get all FieldMappings");
        return fieldMappingRepository.findAll(pageable);
    }

    /**
     * Get one fieldMapping by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FieldMapping> findOne(Long id) {
        log.debug("Request to get FieldMapping : {}", id);
        return fieldMappingRepository.findById(id);
    }

    /**
     * Delete the fieldMapping by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FieldMapping : {}", id);
        fieldMappingRepository.deleteById(id);
    }
}
