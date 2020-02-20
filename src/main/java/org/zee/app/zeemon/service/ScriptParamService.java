package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.ScriptParam;
import org.zee.app.zeemon.repository.ScriptParamRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link ScriptParam}.
 */
@Service
@Transactional
public class ScriptParamService {

    private final Logger log = LoggerFactory.getLogger(ScriptParamService.class);

    private final ScriptParamRepository scriptParamRepository;

    public ScriptParamService(ScriptParamRepository scriptParamRepository) {
        this.scriptParamRepository = scriptParamRepository;
    }

    /**
     * Save a scriptParam.
     *
     * @param scriptParam the entity to save.
     * @return the persisted entity.
     */
    public ScriptParam save(ScriptParam scriptParam) {
        log.debug("Request to save ScriptParam : {}", scriptParam);
        return scriptParamRepository.save(scriptParam);
    }

    /**
     * Get all the scriptParams.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<ScriptParam> findAll(Pageable pageable) {
        log.debug("Request to get all ScriptParams");
        return scriptParamRepository.findAll(pageable);
    }

    /**
     * Get one scriptParam by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<ScriptParam> findOne(Long id) {
        log.debug("Request to get ScriptParam : {}", id);
        return scriptParamRepository.findById(id);
    }

    /**
     * Delete the scriptParam by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete ScriptParam : {}", id);
        scriptParamRepository.deleteById(id);
    }
}
