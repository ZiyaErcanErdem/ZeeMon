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
import org.zee.app.zeemon.domain.ContentValidationError;
import org.zee.app.zeemon.service.ContentValidationErrorService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.ContentValidationError}.
 */
@RestController
@RequestMapping("/api")
public class ContentValidationErrorResource {

    private final Logger log = LoggerFactory.getLogger(ContentValidationErrorResource.class);

    private static final String ENTITY_NAME = "contentValidationError";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContentValidationErrorService contentValidationErrorService;

    public ContentValidationErrorResource(ContentValidationErrorService contentValidationErrorService) {
        this.contentValidationErrorService = contentValidationErrorService;
    }

    /**
     * {@code POST  /content-validation-errors} : Create a new contentValidationError.
     *
     * @param contentValidationError the contentValidationError to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contentValidationError, or with status {@code 400 (Bad Request)} if the contentValidationError has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/content-validation-errors")
    public ResponseEntity<ContentValidationError> createContentValidationError(@Valid @RequestBody ContentValidationError contentValidationError) throws URISyntaxException {
        log.debug("REST request to save ContentValidationError : {}", contentValidationError);
        if (contentValidationError.getId() != null) {
            throw new BadRequestAlertException("A new contentValidationError cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentValidationError result = contentValidationErrorService.save(contentValidationError);
        return ResponseEntity.created(new URI("/api/content-validation-errors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /content-validation-errors} : Updates an existing contentValidationError.
     *
     * @param contentValidationError the contentValidationError to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentValidationError,
     * or with status {@code 400 (Bad Request)} if the contentValidationError is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contentValidationError couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/content-validation-errors")
    public ResponseEntity<ContentValidationError> updateContentValidationError(@Valid @RequestBody ContentValidationError contentValidationError) throws URISyntaxException {
        log.debug("REST request to update ContentValidationError : {}", contentValidationError);
        if (contentValidationError.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContentValidationError result = contentValidationErrorService.save(contentValidationError);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentValidationError.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /content-validation-errors} : get all the contentValidationErrors.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contentValidationErrors in body.
     */
    @GetMapping("/content-validation-errors")
    public ResponseEntity<List<ContentValidationError>> getAllContentValidationErrors(Pageable pageable) {
        log.debug("REST request to get a page of ContentValidationErrors");
        Page<ContentValidationError> page = contentValidationErrorService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /content-validation-errors/:id} : get the "id" contentValidationError.
     *
     * @param id the id of the contentValidationError to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contentValidationError, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/content-validation-errors/{id}")
    public ResponseEntity<ContentValidationError> getContentValidationError(@PathVariable Long id) {
        log.debug("REST request to get ContentValidationError : {}", id);
        Optional<ContentValidationError> contentValidationError = contentValidationErrorService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contentValidationError);
    }

    /**
     * {@code DELETE  /content-validation-errors/:id} : delete the "id" contentValidationError.
     *
     * @param id the id of the contentValidationError to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/content-validation-errors/{id}")
    public ResponseEntity<Void> deleteContentValidationError(@PathVariable Long id) {
        log.debug("REST request to delete ContentValidationError : {}", id);
        contentValidationErrorService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
