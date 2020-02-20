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
import org.zee.app.zeemon.domain.EndpointProperty;
import org.zee.app.zeemon.service.EndpointPropertyService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.EndpointProperty}.
 */
@RestController
@RequestMapping("/api")
public class EndpointPropertyResource {

    private final Logger log = LoggerFactory.getLogger(EndpointPropertyResource.class);

    private static final String ENTITY_NAME = "endpointProperty";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EndpointPropertyService endpointPropertyService;

    public EndpointPropertyResource(EndpointPropertyService endpointPropertyService) {
        this.endpointPropertyService = endpointPropertyService;
    }

    /**
     * {@code POST  /endpoint-properties} : Create a new endpointProperty.
     *
     * @param endpointProperty the endpointProperty to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new endpointProperty, or with status {@code 400 (Bad Request)} if the endpointProperty has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/endpoint-properties")
    public ResponseEntity<EndpointProperty> createEndpointProperty(@Valid @RequestBody EndpointProperty endpointProperty) throws URISyntaxException {
        log.debug("REST request to save EndpointProperty : {}", endpointProperty);
        if (endpointProperty.getId() != null) {
            throw new BadRequestAlertException("A new endpointProperty cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EndpointProperty result = endpointPropertyService.save(endpointProperty);
        return ResponseEntity.created(new URI("/api/endpoint-properties/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /endpoint-properties} : Updates an existing endpointProperty.
     *
     * @param endpointProperty the endpointProperty to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated endpointProperty,
     * or with status {@code 400 (Bad Request)} if the endpointProperty is not valid,
     * or with status {@code 500 (Internal Server Error)} if the endpointProperty couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/endpoint-properties")
    public ResponseEntity<EndpointProperty> updateEndpointProperty(@Valid @RequestBody EndpointProperty endpointProperty) throws URISyntaxException {
        log.debug("REST request to update EndpointProperty : {}", endpointProperty);
        if (endpointProperty.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EndpointProperty result = endpointPropertyService.save(endpointProperty);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, endpointProperty.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /endpoint-properties} : get all the endpointProperties.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of endpointProperties in body.
     */
    @GetMapping("/endpoint-properties")
    public ResponseEntity<List<EndpointProperty>> getAllEndpointProperties(Pageable pageable) {
        log.debug("REST request to get a page of EndpointProperties");
        Page<EndpointProperty> page = endpointPropertyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /endpoint-properties/:id} : get the "id" endpointProperty.
     *
     * @param id the id of the endpointProperty to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the endpointProperty, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/endpoint-properties/{id}")
    public ResponseEntity<EndpointProperty> getEndpointProperty(@PathVariable Long id) {
        log.debug("REST request to get EndpointProperty : {}", id);
        Optional<EndpointProperty> endpointProperty = endpointPropertyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(endpointProperty);
    }

    /**
     * {@code DELETE  /endpoint-properties/:id} : delete the "id" endpointProperty.
     *
     * @param id the id of the endpointProperty to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/endpoint-properties/{id}")
    public ResponseEntity<Void> deleteEndpointProperty(@PathVariable Long id) {
        log.debug("REST request to delete EndpointProperty : {}", id);
        endpointPropertyService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
