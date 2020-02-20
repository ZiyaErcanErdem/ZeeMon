package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.ActionExecution;
import org.zee.app.zeemon.repository.ActionExecutionRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ActionExecution}.
 */
@Service
@Transactional
public class ActionExecutionService {

    private final Logger log = LoggerFactory.getLogger(ActionExecutionService.class);

    private final ActionExecutionRepository actionExecutionRepository;

    public ActionExecutionService(ActionExecutionRepository actionExecutionRepository) {
        this.actionExecutionRepository = actionExecutionRepository;
    }

    /**
     * Save a actionExecution.
     *
     * @param actionExecution the entity to save.
     * @return the persisted entity.
     */
    public ActionExecution save(ActionExecution actionExecution) {
        log.debug("Request to save ActionExecution : {}", actionExecution);
        return actionExecutionRepository.save(actionExecution);
    }

    /**
     * Get all the actionExecutions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActionExecution> findAll(Pageable pageable) {
        log.debug("Request to get all ActionExecutions");
        return actionExecutionRepository.findAll(pageable);
    }

    /**
     * Get one actionExecution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActionExecution> findOne(Long id) {
        log.debug("Request to get ActionExecution : {}", id);
        return actionExecutionRepository.findById(id);
    }

    /**
     * Delete the actionExecution by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ActionExecution : {}", id);
        actionExecutionRepository.deleteById(id);
    }
}
