package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.TaskExecution;
import org.zee.app.zeemon.repository.TaskExecutionRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link TaskExecution}.
 */
@Service
@Transactional
public class TaskExecutionService {

    private final Logger log = LoggerFactory.getLogger(TaskExecutionService.class);

    private final TaskExecutionRepository taskExecutionRepository;

    public TaskExecutionService(TaskExecutionRepository taskExecutionRepository) {
        this.taskExecutionRepository = taskExecutionRepository;
    }

    /**
     * Save a taskExecution.
     *
     * @param taskExecution the entity to save.
     * @return the persisted entity.
     */
    public TaskExecution save(TaskExecution taskExecution) {
        log.debug("Request to save TaskExecution : {}", taskExecution);
        return taskExecutionRepository.save(taskExecution);
    }

    /**
     * Get all the taskExecutions.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<TaskExecution> findAll(Pageable pageable) {
        log.debug("Request to get all TaskExecutions");
        return taskExecutionRepository.findAll(pageable);
    }

    /**
     * Get one taskExecution by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<TaskExecution> findOne(Long id) {
        log.debug("Request to get TaskExecution : {}", id);
        return taskExecutionRepository.findById(id);
    }

    /**
     * Delete the taskExecution by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete TaskExecution : {}", id);
        taskExecutionRepository.deleteById(id);
    }
}
