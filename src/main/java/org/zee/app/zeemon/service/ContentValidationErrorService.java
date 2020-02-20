package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.ContentValidationError;
import org.zee.app.zeemon.repository.ContentValidationErrorRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContentValidationError}.
 */
@Service
@Transactional
public class ContentValidationErrorService {

    private final Logger log = LoggerFactory.getLogger(ContentValidationErrorService.class);

    private final ContentValidationErrorRepository contentValidationErrorRepository;

    public ContentValidationErrorService(ContentValidationErrorRepository contentValidationErrorRepository) {
        this.contentValidationErrorRepository = contentValidationErrorRepository;
    }

    /**
     * Save a contentValidationError.
     *
     * @param contentValidationError the entity to save.
     * @return the persisted entity.
     */
    public ContentValidationError save(ContentValidationError contentValidationError) {
        log.debug("Request to save ContentValidationError : {}", contentValidationError);
        return contentValidationErrorRepository.save(contentValidationError);
    }

    /**
     * Get all the contentValidationErrors.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContentValidationError> findAll(Pageable pageable) {
        log.debug("Request to get all ContentValidationErrors");
        return contentValidationErrorRepository.findAll(pageable);
    }

    /**
     * Get one contentValidationError by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContentValidationError> findOne(Long id) {
        log.debug("Request to get ContentValidationError : {}", id);
        return contentValidationErrorRepository.findById(id);
    }

    /**
     * Delete the contentValidationError by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContentValidationError : {}", id);
        contentValidationErrorRepository.deleteById(id);
    }
}
