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
import org.zee.app.zeemon.domain.FieldMapping;
import org.zee.app.zeemon.service.FieldMappingService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.FieldMapping}.
 */
@RestController
@RequestMapping("/api")
public class FieldMappingResource {

    private final Logger log = LoggerFactory.getLogger(FieldMappingResource.class);

    private static final String ENTITY_NAME = "fieldMapping";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final FieldMappingService fieldMappingService;

    public FieldMappingResource(FieldMappingService fieldMappingService) {
        this.fieldMappingService = fieldMappingService;
    }

    /**
     * {@code POST  /field-mappings} : Create a new fieldMapping.
     *
     * @param fieldMapping the fieldMapping to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new fieldMapping, or with status {@code 400 (Bad Request)} if the fieldMapping has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/field-mappings")
    public ResponseEntity<FieldMapping> createFieldMapping(@Valid @RequestBody FieldMapping fieldMapping) throws URISyntaxException {
        log.debug("REST request to save FieldMapping : {}", fieldMapping);
        if (fieldMapping.getId() != null) {
            throw new BadRequestAlertException("A new fieldMapping cannot already have an ID", ENTITY_NAME, "idexists");
        }
        FieldMapping result = fieldMappingService.save(fieldMapping);
        return ResponseEntity.created(new URI("/api/field-mappings/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /field-mappings} : Updates an existing fieldMapping.
     *
     * @param fieldMapping the fieldMapping to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated fieldMapping,
     * or with status {@code 400 (Bad Request)} if the fieldMapping is not valid,
     * or with status {@code 500 (Internal Server Error)} if the fieldMapping couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/field-mappings")
    public ResponseEntity<FieldMapping> updateFieldMapping(@Valid @RequestBody FieldMapping fieldMapping) throws URISyntaxException {
        log.debug("REST request to update FieldMapping : {}", fieldMapping);
        if (fieldMapping.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        FieldMapping result = fieldMappingService.save(fieldMapping);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, fieldMapping.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /field-mappings} : get all the fieldMappings.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of fieldMappings in body.
     */
    @GetMapping("/field-mappings")
    public ResponseEntity<List<FieldMapping>> getAllFieldMappings(Pageable pageable) {
        log.debug("REST request to get a page of FieldMappings");
        Page<FieldMapping> page = fieldMappingService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /field-mappings/:id} : get the "id" fieldMapping.
     *
     * @param id the id of the fieldMapping to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the fieldMapping, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/field-mappings/{id}")
    public ResponseEntity<FieldMapping> getFieldMapping(@PathVariable Long id) {
        log.debug("REST request to get FieldMapping : {}", id);
        Optional<FieldMapping> fieldMapping = fieldMappingService.findOne(id);
        return ResponseUtil.wrapOrNotFound(fieldMapping);
    }

    /**
     * {@code DELETE  /field-mappings/:id} : delete the "id" fieldMapping.
     *
     * @param id the id of the fieldMapping to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/field-mappings/{id}")
    public ResponseEntity<Void> deleteFieldMapping(@PathVariable Long id) {
        log.debug("REST request to delete FieldMapping : {}", id);
        fieldMappingService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
