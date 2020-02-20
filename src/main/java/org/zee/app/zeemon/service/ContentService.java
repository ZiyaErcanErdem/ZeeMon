package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.Content;
import org.zee.app.zeemon.repository.ContentRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link Content}.
 */
@Service
@Transactional
public class ContentService {

    private final Logger log = LoggerFactory.getLogger(ContentService.class);

    private final ContentRepository contentRepository;

    public ContentService(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    /**
     * Save a content.
     *
     * @param content the entity to save.
     * @return the persisted entity.
     */
    public Content save(Content content) {
        log.debug("Request to save Content : {}", content);
        return contentRepository.save(content);
    }

    /**
     * Get all the contents.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<Content> findAll(Pageable pageable) {
        log.debug("Request to get all Contents");
        return contentRepository.findAll(pageable);
    }

    /**
     * Get one content by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<Content> findOne(Long id) {
        log.debug("Request to get Content : {}", id);
        return contentRepository.findById(id);
    }

    /**
     * Delete the content by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete Content : {}", id);
        contentRepository.deleteById(id);
    }
}
