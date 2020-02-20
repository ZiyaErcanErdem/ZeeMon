package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.FlowExecution;
import org.zee.app.zeemon.repository.FlowExecutionRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link FlowExecution}.
 */
@Service
@Transactional
public class FlowExecutionService {

    private final Logger log = LoggerFactory.getLogger(FlowExecutionService.class);

    private final FlowExecutionRepository flowExecutionRepository;

    public FlowExecutionService(FlowExecutionRepository flowExecutionRepository) {
        this.flowExecutionRepository = flowExecutionRepository;
    }

    /**
     * Save a flowExecution.
     *
     * @param flowExecution the entity to save.
     * @return the persisted entity.
     */
    public FlowExecution save(FlowExecution flowExecution) {
        log.debug("Request to save FlowExecution : {}", flowExecution);
        return flowExecutionRepository.save(flowExecution);
    }

    /**
     * Get all the flowExecutions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<FlowExecution> findAll(Pageable pageable) {
        log.debug("Request to get all FlowExecutions");
        return flowExecutionRepository.findAll(pageable);
    }

    /**
     * Get one flowExecution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<FlowExecution> findOne(Long id) {
        log.debug("Request to get FlowExecution : {}", id);
        return flowExecutionRepository.findById(id);
    }

    /**
     * Delete the flowExecution by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete FlowExecution : {}", id);
        flowExecutionRepository.deleteById(id);
    }
}
