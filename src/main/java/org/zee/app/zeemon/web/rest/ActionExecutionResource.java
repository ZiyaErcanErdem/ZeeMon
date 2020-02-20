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
import org.zee.app.zeemon.domain.ActionExecution;
import org.zee.app.zeemon.service.ActionExecutionService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.ActionExecution}.
 */
@RestController
@RequestMapping("/api")
public class ActionExecutionResource {

    private final Logger log = LoggerFactory.getLogger(ActionExecutionResource.class);

    private static final String ENTITY_NAME = "actionExecution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActionExecutionService actionExecutionService;

    public ActionExecutionResource(ActionExecutionService actionExecutionService) {
        this.actionExecutionService = actionExecutionService;
    }

    /**
     * {@code POST  /action-executions} : Create a new actionExecution.
     *
     * @param actionExecution the actionExecution to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actionExecution, or with status {@code 400 (Bad Request)} if the actionExecution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/action-executions")
    public ResponseEntity<ActionExecution> createActionExecution(@Valid @RequestBody ActionExecution actionExecution) throws URISyntaxException {
        log.debug("REST request to save ActionExecution : {}", actionExecution);
        if (actionExecution.getId() != null) {
            throw new BadRequestAlertException("A new actionExecution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActionExecution result = actionExecutionService.save(actionExecution);
        return ResponseEntity.created(new URI("/api/action-executions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /action-executions} : Updates an existing actionExecution.
     *
     * @param actionExecution the actionExecution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actionExecution,
     * or with status {@code 400 (Bad Request)} if the actionExecution is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actionExecution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/action-executions")
    public ResponseEntity<ActionExecution> updateActionExecution(@Valid @RequestBody ActionExecution actionExecution) throws URISyntaxException {
        log.debug("REST request to update ActionExecution : {}", actionExecution);
        if (actionExecution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActionExecution result = actionExecutionService.save(actionExecution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actionExecution.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /action-executions} : get all the actionExecutions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actionExecutions in body.
     */
    @GetMapping("/action-executions")
    public ResponseEntity<List<ActionExecution>> getAllActionExecutions(Pageable pageable) {
        log.debug("REST request to get a page of ActionExecutions");
        Page<ActionExecution> page = actionExecutionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /action-executions/:id} : get the "id" actionExecution.
     *
     * @param id the id of the actionExecution to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actionExecution, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/action-executions/{id}")
    public ResponseEntity<ActionExecution> getActionExecution(@PathVariable Long id) {
        log.debug("REST request to get ActionExecution : {}", id);
        Optional<ActionExecution> actionExecution = actionExecutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actionExecution);
    }

    /**
     * {@code DELETE  /action-executions/:id} : delete the "id" actionExecution.
     *
     * @param id the id of the actionExecution to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/action-executions/{id}")
    public ResponseEntity<Void> deleteActionExecution(@PathVariable Long id) {
        log.debug("REST request to delete ActionExecution : {}", id);
        actionExecutionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
