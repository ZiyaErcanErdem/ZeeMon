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
import org.zee.app.zeemon.domain.Endpoint;
import org.zee.app.zeemon.service.EndpointService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.Endpoint}.
 */
@RestController
@RequestMapping("/api")
public class EndpointResource {

    private final Logger log = LoggerFactory.getLogger(EndpointResource.class);

    private static final String ENTITY_NAME = "endpoint";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EndpointService endpointService;

    public EndpointResource(EndpointService endpointService) {
        this.endpointService = endpointService;
    }

    /**
     * {@code POST  /endpoints} : Create a new endpoint.
     *
     * @param endpoint the endpoint to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new endpoint, or with status {@code 400 (Bad Request)} if the endpoint has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/endpoints")
    public ResponseEntity<Endpoint> createEndpoint(@Valid @RequestBody Endpoint endpoint) throws URISyntaxException {
        log.debug("REST request to save Endpoint : {}", endpoint);
        if (endpoint.getId() != null) {
            throw new BadRequestAlertException("A new endpoint cannot already have an ID", ENTITY_NAME, "idexists");
        }
        Endpoint result = endpointService.save(endpoint);
        return ResponseEntity.created(new URI("/api/endpoints/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /endpoints} : Updates an existing endpoint.
     *
     * @param endpoint the endpoint to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated endpoint,
     * or with status {@code 400 (Bad Request)} if the endpoint is not valid,
     * or with status {@code 500 (Internal Server Error)} if the endpoint couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/endpoints")
    public ResponseEntity<Endpoint> updateEndpoint(@Valid @RequestBody Endpoint endpoint) throws URISyntaxException {
        log.debug("REST request to update Endpoint : {}", endpoint);
        if (endpoint.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        Endpoint result = endpointService.save(endpoint);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, endpoint.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /endpoints} : get all the endpoints.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of endpoints in body.
     */
    @GetMapping("/endpoints")
    public ResponseEntity<List<Endpoint>> getAllEndpoints(Pageable pageable) {
        log.debug("REST request to get a page of Endpoints");
        Page<Endpoint> page = endpointService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /endpoints/:id} : get the "id" endpoint.
     *
     * @param id the id of the endpoint to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the endpoint, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/endpoints/{id}")
    public ResponseEntity<Endpoint> getEndpoint(@PathVariable Long id) {
        log.debug("REST request to get Endpoint : {}", id);
        Optional<Endpoint> endpoint = endpointService.findOne(id);
        return ResponseUtil.wrapOrNotFound(endpoint);
    }

    /**
     * {@code DELETE  /endpoints/:id} : delete the "id" endpoint.
     *
     * @param id the id of the endpoint to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/endpoints/{id}")
    public ResponseEntity<Void> deleteEndpoint(@PathVariable Long id) {
        log.debug("REST request to delete Endpoint : {}", id);
        endpointService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
