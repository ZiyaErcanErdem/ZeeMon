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
import org.zee.app.zeemon.domain.CheckScript;
import org.zee.app.zeemon.service.CheckScriptService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.CheckScript}.
 */
@RestController
@RequestMapping("/api")
public class CheckScriptResource {

    private final Logger log = LoggerFactory.getLogger(CheckScriptResource.class);

    private static final String ENTITY_NAME = "checkScript";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final CheckScriptService checkScriptService;

    public CheckScriptResource(CheckScriptService checkScriptService) {
        this.checkScriptService = checkScriptService;
    }

    /**
     * {@code POST  /check-scripts} : Create a new checkScript.
     *
     * @param checkScript the checkScript to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new checkScript, or with status {@code 400 (Bad Request)} if the checkScript has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/check-scripts")
    public ResponseEntity<CheckScript> createCheckScript(@Valid @RequestBody CheckScript checkScript) throws URISyntaxException {
        log.debug("REST request to save CheckScript : {}", checkScript);
        if (checkScript.getId() != null) {
            throw new BadRequestAlertException("A new checkScript cannot already have an ID", ENTITY_NAME, "idexists");
        }
        CheckScript result = checkScriptService.save(checkScript);
        return ResponseEntity.created(new URI("/api/check-scripts/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /check-scripts} : Updates an existing checkScript.
     *
     * @param checkScript the checkScript to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated checkScript,
     * or with status {@code 400 (Bad Request)} if the checkScript is not valid,
     * or with status {@code 500 (Internal Server Error)} if the checkScript couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/check-scripts")
    public ResponseEntity<CheckScript> updateCheckScript(@Valid @RequestBody CheckScript checkScript) throws URISyntaxException {
        log.debug("REST request to update CheckScript : {}", checkScript);
        if (checkScript.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        CheckScript result = checkScriptService.save(checkScript);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, checkScript.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /check-scripts} : get all the checkScripts.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of checkScripts in body.
     */
    @GetMapping("/check-scripts")
    public ResponseEntity<List<CheckScript>> getAllCheckScripts(Pageable pageable) {
        log.debug("REST request to get a page of CheckScripts");
        Page<CheckScript> page = checkScriptService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /check-scripts/:id} : get the "id" checkScript.
     *
     * @param id the id of the checkScript to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the checkScript, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/check-scripts/{id}")
    public ResponseEntity<CheckScript> getCheckScript(@PathVariable Long id) {
        log.debug("REST request to get CheckScript : {}", id);
        Optional<CheckScript> checkScript = checkScriptService.findOne(id);
        return ResponseUtil.wrapOrNotFound(checkScript);
    }

    /**
     * {@code DELETE  /check-scripts/:id} : delete the "id" checkScript.
     *
     * @param id the id of the checkScript to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/check-scripts/{id}")
    public ResponseEntity<Void> deleteCheckScript(@PathVariable Long id) {
        log.debug("REST request to delete CheckScript : {}", id);
        checkScriptService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
