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
import org.zee.app.zeemon.domain.ActionScript;
import org.zee.app.zeemon.service.ActionScriptService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.ActionScript}.
 */
@RestController
@RequestMapping("/api")
public class ActionScriptResource {

    private final Logger log = LoggerFactory.getLogger(ActionScriptResource.class);

    private static final String ENTITY_NAME = "actionScript";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActionScriptService actionScriptService;

    public ActionScriptResource(ActionScriptService actionScriptService) {
        this.actionScriptService = actionScriptService;
    }

    /**
     * {@code POST  /action-scripts} : Create a new actionScript.
     *
     * @param actionScript the actionScript to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actionScript, or with status {@code 400 (Bad Request)} if the actionScript has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/action-scripts")
    public ResponseEntity<ActionScript> createActionScript(@Valid @RequestBody ActionScript actionScript) throws URISyntaxException {
        log.debug("REST request to save ActionScript : {}", actionScript);
        if (actionScript.getId() != null) {
            throw new BadRequestAlertException("A new actionScript cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActionScript result = actionScriptService.save(actionScript);
        return ResponseEntity.created(new URI("/api/action-scripts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /action-scripts} : Updates an existing actionScript.
     *
     * @param actionScript the actionScript to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actionScript,
     * or with status {@code 400 (Bad Request)} if the actionScript is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actionScript couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/action-scripts")
    public ResponseEntity<ActionScript> updateActionScript(@Valid @RequestBody ActionScript actionScript) throws URISyntaxException {
        log.debug("REST request to update ActionScript : {}", actionScript);
        if (actionScript.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActionScript result = actionScriptService.save(actionScript);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actionScript.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /action-scripts} : get all the actionScripts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actionScripts in body.
     */
    @GetMapping("/action-scripts")
    public ResponseEntity<List<ActionScript>> getAllActionScripts(Pageable pageable) {
        log.debug("REST request to get a page of ActionScripts");
        Page<ActionScript> page = actionScriptService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /action-scripts/:id} : get the "id" actionScript.
     *
     * @param id the id of the actionScript to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actionScript, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/action-scripts/{id}")
    public ResponseEntity<ActionScript> getActionScript(@PathVariable Long id) {
        log.debug("REST request to get ActionScript : {}", id);
        Optional<ActionScript> actionScript = actionScriptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actionScript);
    }

    /**
     * {@code DELETE  /action-scripts/:id} : delete the "id" actionScript.
     *
     * @param id the id of the actionScript to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/action-scripts/{id}")
    public ResponseEntity<Void> deleteActionScript(@PathVariable Long id) {
        log.debug("REST request to delete ActionScript : {}", id);
        actionScriptService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
