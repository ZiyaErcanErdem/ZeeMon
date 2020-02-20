package org.zee.app.zeemon.web.rest;

import io.github.jhipster.web.util.HeaderUtil;
import io.github.jhipster.web.util.PaginationUtil;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import org.zee.app.zeemon.domain.TaskExecution;
import org.zee.app.zeemon.service.TaskExecutionService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.TaskExecution}.
 */
@RestController
@RequestMapping("/api")
public class TaskExecutionResource {

    private final Logger log = LoggerFactory.getLogger(TaskExecutionResource.class);

    private static final String ENTITY_NAME = "taskExecution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final TaskExecutionService taskExecutionService;

    public TaskExecutionResource(TaskExecutionService taskExecutionService) {
        this.taskExecutionService = taskExecutionService;
    }

    /**
     * {@code POST  /task-executions} : Create a new taskExecution.
     *
     * @param taskExecution the taskExecution to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new taskExecution, or with status {@code 400 (Bad Request)} if the taskExecution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/task-executions")
    public ResponseEntity<TaskExecution> createTaskExecution(@Valid @RequestBody TaskExecution taskExecution) throws URISyntaxException {
        log.debug("REST request to save TaskExecution : {}", taskExecution);
        if (taskExecution.getId() != null) {
            throw new BadRequestAlertException("A new taskExecution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        TaskExecution result = taskExecutionService.save(taskExecution);
        return ResponseEntity.created(new URI("/api/task-executions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /task-executions} : Updates an existing taskExecution.
     *
     * @param taskExecution the taskExecution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated taskExecution,
     * or with status {@code 400 (Bad Request)} if the taskExecution is not valid,
     * or with status {@code 500 (Internal Server Error)} if the taskExecution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/task-executions")
    public ResponseEntity<TaskExecution> updateTaskExecution(@Valid @RequestBody TaskExecution taskExecution) throws URISyntaxException {
        log.debug("REST request to update TaskExecution : {}", taskExecution);
        if (taskExecution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        TaskExecution result = taskExecutionService.save(taskExecution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, taskExecution.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /task-executions} : get all the taskExecutions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of taskExecutions in body.
     */
    @GetMapping("/task-executions")
    public ResponseEntity<List<TaskExecution>> getAllTaskExecutions(Pageable pageable) {
        log.debug("REST request to get a page of TaskExecutions");
        Page<TaskExecution> page = taskExecutionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /task-executions/:id} : get the "id" taskExecution.
     *
     * @param id the id of the taskExecution to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the taskExecution, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/task-executions/{id}")
    public ResponseEntity<TaskExecution> getTaskExecution(@PathVariable Long id) {
        log.debug("REST request to get TaskExecution : {}", id);
        Optional<TaskExecution> taskExecution = taskExecutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(taskExecution);
    }

    /**
     * {@code DELETE  /task-executions/:id} : delete the "id" taskExecution.
     *
     * @param id the id of the taskExecution to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/task-executions/{id}")
    public ResponseEntity<Void> deleteTaskExecution(@PathVariable Long id) {
        log.debug("REST request to delete TaskExecution : {}", id);
        taskExecutionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
