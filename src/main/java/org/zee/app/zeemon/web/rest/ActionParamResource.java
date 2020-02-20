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
import org.zee.app.zeemon.domain.ActionParam;
import org.zee.app.zeemon.service.ActionParamService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.ActionParam}.
 */
@RestController
@RequestMapping("/api")
public class ActionParamResource {

    private final Logger log = LoggerFactory.getLogger(ActionParamResource.class);

    private static final String ENTITY_NAME = "actionParam";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ActionParamService actionParamService;

    public ActionParamResource(ActionParamService actionParamService) {
        this.actionParamService = actionParamService;
    }

    /**
     * {@code POST  /action-params} : Create a new actionParam.
     *
     * @param actionParam the actionParam to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new actionParam, or with status {@code 400 (Bad Request)} if the actionParam has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/action-params")
    public ResponseEntity<ActionParam> createActionParam(@Valid @RequestBody ActionParam actionParam) throws URISyntaxException {
        log.debug("REST request to save ActionParam : {}", actionParam);
        if (actionParam.getId() != null) {
            throw new BadRequestAlertException("A new actionParam cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ActionParam result = actionParamService.save(actionParam);
        return ResponseEntity.created(new URI("/api/action-params/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /action-params} : Updates an existing actionParam.
     *
     * @param actionParam the actionParam to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated actionParam,
     * or with status {@code 400 (Bad Request)} if the actionParam is not valid,
     * or with status {@code 500 (Internal Server Error)} if the actionParam couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/action-params")
    public ResponseEntity<ActionParam> updateActionParam(@Valid @RequestBody ActionParam actionParam) throws URISyntaxException {
        log.debug("REST request to update ActionParam : {}", actionParam);
        if (actionParam.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ActionParam result = actionParamService.save(actionParam);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, actionParam.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /action-params} : get all the actionParams.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of actionParams in body.
     */
    @GetMapping("/action-params")
    public ResponseEntity<List<ActionParam>> getAllActionParams(Pageable pageable) {
        log.debug("REST request to get a page of ActionParams");
        Page<ActionParam> page = actionParamService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /action-params/:id} : get the "id" actionParam.
     *
     * @param id the id of the actionParam to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the actionParam, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/action-params/{id}")
    public ResponseEntity<ActionParam> getActionParam(@PathVariable Long id) {
        log.debug("REST request to get ActionParam : {}", id);
        Optional<ActionParam> actionParam = actionParamService.findOne(id);
        return ResponseUtil.wrapOrNotFound(actionParam);
    }

    /**
     * {@code DELETE  /action-params/:id} : delete the "id" actionParam.
     *
     * @param id the id of the actionParam to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/action-params/{id}")
    public ResponseEntity<Void> deleteActionParam(@PathVariable Long id) {
        log.debug("REST request to delete ActionParam : {}", id);
        actionParamService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
