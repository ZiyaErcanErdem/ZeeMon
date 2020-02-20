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
import org.zee.app.zeemon.domain.ScriptParam;
import org.zee.app.zeemon.service.ScriptParamService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.ScriptParam}.
 */
@RestController
@RequestMapping("/api")
public class ScriptParamResource {

    private final Logger log = LoggerFactory.getLogger(ScriptParamResource.class);

    private static final String ENTITY_NAME = "scriptParam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ScriptParamService scriptParamService;

    public ScriptParamResource(ScriptParamService scriptParamService) {
        this.scriptParamService = scriptParamService;
    }

    /**
     * {@code POST  /script-params} : Create a new scriptParam.
     *
     * @param scriptParam the scriptParam to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new scriptParam, or with status {@code 400 (Bad Request)} if the scriptParam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/script-params")
    public ResponseEntity<ScriptParam> createScriptParam(@Valid @RequestBody ScriptParam scriptParam) throws URISyntaxException {
        log.debug("REST request to save ScriptParam : {}", scriptParam);
        if (scriptParam.getId() != null) {
            throw new BadRequestAlertException("A new scriptParam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ScriptParam result = scriptParamService.save(scriptParam);
        return ResponseEntity.created(new URI("/api/script-params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /script-params} : Updates an existing scriptParam.
     *
     * @param scriptParam the scriptParam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated scriptParam,
     * or with status {@code 400 (Bad Request)} if the scriptParam is not valid,
     * or with status {@code 500 (Internal Server Error)} if the scriptParam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/script-params")
    public ResponseEntity<ScriptParam> updateScriptParam(@Valid @RequestBody ScriptParam scriptParam) throws URISyntaxException {
        log.debug("REST request to update ScriptParam : {}", scriptParam);
        if (scriptParam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ScriptParam result = scriptParamService.save(scriptParam);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, scriptParam.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /script-params} : get all the scriptParams.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of scriptParams in body.
     */
    @GetMapping("/script-params")
    public ResponseEntity<List<ScriptParam>> getAllScriptParams(Pageable pageable) {
        log.debug("REST request to get a page of ScriptParams");
        Page<ScriptParam> page = scriptParamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /script-params/:id} : get the "id" scriptParam.
     *
     * @param id the id of the scriptParam to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the scriptParam, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/script-params/{id}")
    public ResponseEntity<ScriptParam> getScriptParam(@PathVariable Long id) {
        log.debug("REST request to get ScriptParam : {}", id);
        Optional<ScriptParam> scriptParam = scriptParamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(scriptParam);
    }

    /**
     * {@code DELETE  /script-params/:id} : delete the "id" scriptParam.
     *
     * @param id the id of the scriptParam to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/script-params/{id}")
    public ResponseEntity<Void> deleteScriptParam(@PathVariable Long id) {
        log.debug("REST request to delete ScriptParam : {}", id);
        scriptParamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
