package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.ActionScript;
import org.zee.app.zeemon.repository.ActionScriptRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ActionScript}.
 */
@Service
@Transactional
public class ActionScriptService {

    private final Logger log = LoggerFactory.getLogger(ActionScriptService.class);

    private final ActionScriptRepository actionScriptRepository;

    public ActionScriptService(ActionScriptRepository actionScriptRepository) {
        this.actionScriptRepository = actionScriptRepository;
    }

    /**
     * Save a actionScript.
     *
     * @param actionScript the entity to save.
     * @return the persisted entity.
     */
    public ActionScript save(ActionScript actionScript) {
        log.debug("Request to save ActionScript : {}", actionScript);
        return actionScriptRepository.save(actionScript);
    }

    /**
     * Get all the actionScripts.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActionScript> findAll(Pageable pageable) {
        log.debug("Request to get all ActionScripts");
        return actionScriptRepository.findAll(pageable);
    }

    /**
     * Get one actionScript by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActionScript> findOne(Long id) {
        log.debug("Request to get ActionScript : {}", id);
        return actionScriptRepository.findById(id);
    }

    /**
     * Delete the actionScript by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ActionScript : {}", id);
        actionScriptRepository.deleteById(id);
    }
}
