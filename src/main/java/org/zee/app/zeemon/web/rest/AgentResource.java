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
import org.zee.app.zeemon.domain.Agent;
import org.zee.app.zeemon.service.AgentService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.Agent}.
 */
@RestController
@RequestMapping("/api")
public class AgentResource {

    private final Logger log = LoggerFactory.getLogger(AgentResource.class);

    private static final String ENTITY_NAME = "agent";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final AgentService agentService;

    public AgentResource(AgentService agentService) {
        this.agentService = agentService;
    }

    /**
     * {@code POST  /agents} : Create a new agent.
     *
     * @param agent the agent to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new agent, or with status {@code 400 (Bad Request)} if the agent has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/agents")
    public ResponseEntity<Agent> createAgent(@Valid @RequestBody Agent agent) throws URISyntaxException {
        log.debug("REST request to save Agent : {}", agent);
        if (agent.getId() != null) {
            throw new BadRequestAlertException("A new agent cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Agent result = agentService.save(agent);
        return ResponseEntity.created(new URI("/api/agents/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /agents} : Updates an existing agent.
     *
     * @param agent the agent to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated agent,
     * or with status {@code 400 (Bad Request)} if the agent is not valid,
     * or with status {@code 500 (Internal Server Error)} if the agent couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/agents")
    public ResponseEntity<Agent> updateAgent(@Valid @RequestBody Agent agent) throws URISyntaxException {
        log.debug("REST request to update Agent : {}", agent);
        if (agent.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Agent result = agentService.save(agent);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, agent.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /agents} : get all the agents.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of agents in body.
     */
    @GetMapping("/agents")
    public ResponseEntity<List<Agent>> getAllAgents(Pageable pageable) {
        log.debug("REST request to get a page of Agents");
        Page<Agent> page = agentService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /agents/:id} : get the "id" agent.
     *
     * @param id the id of the agent to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the agent, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/agents/{id}")
    public ResponseEntity<Agent> getAgent(@PathVariable Long id) {
        log.debug("REST request to get Agent : {}", id);
        Optional<Agent> agent = agentService.findOne(id);
        return ResponseUtil.wrapOrNotFound(agent);
    }

    /**
     * {@code DELETE  /agents/:id} : delete the "id" agent.
     *
     * @param id the id of the agent to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/agents/{id}")
    public ResponseEntity<Void> deleteAgent(@PathVariable Long id) {
        log.debug("REST request to delete Agent : {}", id);
        agentService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
