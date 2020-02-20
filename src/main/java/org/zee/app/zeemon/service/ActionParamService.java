package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.ActionParam;
import org.zee.app.zeemon.repository.ActionParamRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ActionParam}.
 */
@Service
@Transactional
public class ActionParamService {

    private final Logger log = LoggerFactory.getLogger(ActionParamService.class);

    private final ActionParamRepository actionParamRepository;

    public ActionParamService(ActionParamRepository actionParamRepository) {
        this.actionParamRepository = actionParamRepository;
    }

    /**
     * Save a actionParam.
     *
     * @param actionParam the entity to save.
     * @return the persisted entity.
     */
    public ActionParam save(ActionParam actionParam) {
        log.debug("Request to save ActionParam : {}", actionParam);
        return actionParamRepository.save(actionParam);
    }

    /**
     * Get all the actionParams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ActionParam> findAll(Pageable pageable) {
        log.debug("Request to get all ActionParams");
        return actionParamRepository.findAll(pageable);
    }

    /**
     * Get one actionParam by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ActionParam> findOne(Long id) {
        log.debug("Request to get ActionParam : {}", id);
        return actionParamRepository.findById(id);
    }

    /**
     * Delete the actionParam by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ActionParam : {}", id);
        actionParamRepository.deleteById(id);
    }
}
