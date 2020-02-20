package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.CheckScript;
import org.zee.app.zeemon.repository.CheckScriptRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link CheckScript}.
 */
@Service
@Transactional
public class CheckScriptService {

    private final Logger log = LoggerFactory.getLogger(CheckScriptService.class);

    private final CheckScriptRepository checkScriptRepository;

    public CheckScriptService(CheckScriptRepository checkScriptRepository) {
        this.checkScriptRepository = checkScriptRepository;
    }

    /**
     * Save a checkScript.
     *
     * @param checkScript the entity to save.
     * @return the persisted entity.
     */
    public CheckScript save(CheckScript checkScript) {
        log.debug("Request to save CheckScript : {}", checkScript);
        return checkScriptRepository.save(checkScript);
    }

    /**
     * Get all the checkScripts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<CheckScript> findAll(Pageable pageable) {
        log.debug("Request to get all CheckScripts");
        return checkScriptRepository.findAll(pageable);
    }

    /**
     * Get one checkScript by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<CheckScript> findOne(Long id) {
        log.debug("Request to get CheckScript : {}", id);
        return checkScriptRepository.findById(id);
    }

    /**
     * Delete the checkScript by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete CheckScript : {}", id);
        checkScriptRepository.deleteById(id);
    }
}
