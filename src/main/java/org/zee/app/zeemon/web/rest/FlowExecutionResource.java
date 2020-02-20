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
import org.zee.app.zeemon.domain.FlowExecution;
import org.zee.app.zeemon.service.FlowExecutionService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.FlowExecution}.
 */
@RestController
@RequestMapping("/api")
public class FlowExecutionResource {

    private final Logger log = LoggerFactory.getLogger(FlowExecutionResource.class);

    private static final String ENTITY_NAME = "flowExecution";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FlowExecutionService flowExecutionService;

    public FlowExecutionResource(FlowExecutionService flowExecutionService) {
        this.flowExecutionService = flowExecutionService;
    }

    /**
     * {@code POST  /flow-executions} : Create a new flowExecution.
     *
     * @param flowExecution the flowExecution to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new flowExecution, or with status {@code 400 (Bad Request)} if the flowExecution has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/flow-executions")
    public ResponseEntity<FlowExecution> createFlowExecution(@Valid @RequestBody FlowExecution flowExecution) throws URISyntaxException {
        log.debug("REST request to save FlowExecution : {}", flowExecution);
        if (flowExecution.getId() != null) {
            throw new BadRequestAlertException("A new flowExecution cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FlowExecution result = flowExecutionService.save(flowExecution);
        return ResponseEntity.created(new URI("/api/flow-executions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /flow-executions} : Updates an existing flowExecution.
     *
     * @param flowExecution the flowExecution to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flowExecution,
     * or with status {@code 400 (Bad Request)} if the flowExecution is not valid,
     * or with status {@code 500 (Internal Server Error)} if the flowExecution couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/flow-executions")
    public ResponseEntity<FlowExecution> updateFlowExecution(@Valid @RequestBody FlowExecution flowExecution) throws URISyntaxException {
        log.debug("REST request to update FlowExecution : {}", flowExecution);
        if (flowExecution.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FlowExecution result = flowExecutionService.save(flowExecution);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, flowExecution.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /flow-executions} : get all the flowExecutions.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of flowExecutions in body.
     */
    @GetMapping("/flow-executions")
    public ResponseEntity<List<FlowExecution>> getAllFlowExecutions(Pageable pageable) {
        log.debug("REST request to get a page of FlowExecutions");
        Page<FlowExecution> page = flowExecutionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /flow-executions/:id} : get the "id" flowExecution.
     *
     * @param id the id of the flowExecution to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the flowExecution, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/flow-executions/{id}")
    public ResponseEntity<FlowExecution> getFlowExecution(@PathVariable Long id) {
        log.debug("REST request to get FlowExecution : {}", id);
        Optional<FlowExecution> flowExecution = flowExecutionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(flowExecution);
    }

    /**
     * {@code DELETE  /flow-executions/:id} : delete the "id" flowExecution.
     *
     * @param id the id of the flowExecution to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/flow-executions/{id}")
    public ResponseEntity<Void> deleteFlowExecution(@PathVariable Long id) {
        log.debug("REST request to delete FlowExecution : {}", id);
        flowExecutionService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
