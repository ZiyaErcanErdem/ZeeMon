package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.ContentMapper;
import org.zee.app.zeemon.repository.ContentMapperRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ContentMapper}.
 */
@Service
@Transactional
public class ContentMapperService {

    private final Logger log = LoggerFactory.getLogger(ContentMapperService.class);

    private final ContentMapperRepository contentMapperRepository;

    public ContentMapperService(ContentMapperRepository contentMapperRepository) {
        this.contentMapperRepository = contentMapperRepository;
    }

    /**
     * Save a contentMapper.
     *
     * @param contentMapper the entity to save.
     * @return the persisted entity.
     */
    public ContentMapper save(ContentMapper contentMapper) {
        log.debug("Request to save ContentMapper : {}", contentMapper);
        return contentMapperRepository.save(contentMapper);
    }

    /**
     * Get all the contentMappers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ContentMapper> findAll(Pageable pageable) {
        log.debug("Request to get all ContentMappers");
        return contentMapperRepository.findAll(pageable);
    }

    /**
     * Get one contentMapper by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ContentMapper> findOne(Long id) {
        log.debug("Request to get ContentMapper : {}", id);
        return contentMapperRepository.findById(id);
    }

    /**
     * Delete the contentMapper by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ContentMapper : {}", id);
        contentMapperRepository.deleteById(id);
    }
}
