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
import org.zee.app.zeemon.domain.Flow;
import org.zee.app.zeemon.service.FlowService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.Flow}.
 */
@RestController
@RequestMapping("/api")
public class FlowResource {

    private final Logger log = LoggerFactory.getLogger(FlowResource.class);

    private static final String ENTITY_NAME = "flow";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FlowService flowService;

    public FlowResource(FlowService flowService) {
        this.flowService = flowService;
    }

    /**
     * {@code POST  /flows} : Create a new flow.
     *
     * @param flow the flow to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new flow, or with status {@code 400 (Bad Request)} if the flow has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/flows")
    public ResponseEntity<Flow> createFlow(@Valid @RequestBody Flow flow) throws URISyntaxException {
        log.debug("REST request to save Flow : {}", flow);
        if (flow.getId() != null) {
            throw new BadRequestAlertException("A new flow cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Flow result = flowService.save(flow);
        return ResponseEntity.created(new URI("/api/flows/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /flows} : Updates an existing flow.
     *
     * @param flow the flow to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated flow,
     * or with status {@code 400 (Bad Request)} if the flow is not valid,
     * or with status {@code 500 (Internal Server Error)} if the flow couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/flows")
    public ResponseEntity<Flow> updateFlow(@Valid @RequestBody Flow flow) throws URISyntaxException {
        log.debug("REST request to update Flow : {}", flow);
        if (flow.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Flow result = flowService.save(flow);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, flow.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /flows} : get all the flows.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of flows in body.
     */
    @GetMapping("/flows")
    public ResponseEntity<List<Flow>> getAllFlows(Pageable pageable) {
        log.debug("REST request to get a page of Flows");
        Page<Flow> page = flowService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /flows/:id} : get the "id" flow.
     *
     * @param id the id of the flow to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the flow, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/flows/{id}")
    public ResponseEntity<Flow> getFlow(@PathVariable Long id) {
        log.debug("REST request to get Flow : {}", id);
        Optional<Flow> flow = flowService.findOne(id);
        return ResponseUtil.wrapOrNotFound(flow);
    }

    /**
     * {@code DELETE  /flows/:id} : delete the "id" flow.
     *
     * @param id the id of the flow to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/flows/{id}")
    public ResponseEntity<Void> deleteFlow(@PathVariable Long id) {
        log.debug("REST request to delete Flow : {}", id);
        flowService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
