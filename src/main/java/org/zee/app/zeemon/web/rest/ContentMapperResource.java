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
import org.zee.app.zeemon.domain.ContentMapper;
import org.zee.app.zeemon.service.ContentMapperService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.ContentMapper}.
 */
@RestController
@RequestMapping("/api")
public class ContentMapperResource {

    private final Logger log = LoggerFactory.getLogger(ContentMapperResource.class);

    private static final String ENTITY_NAME = "contentMapper";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final ContentMapperService contentMapperService;

    public ContentMapperResource(ContentMapperService contentMapperService) {
        this.contentMapperService = contentMapperService;
    }

    /**
     * {@code POST  /content-mappers} : Create a new contentMapper.
     *
     * @param contentMapper the contentMapper to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new contentMapper, or with status {@code 400 (Bad Request)} if the contentMapper has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/content-mappers")
    public ResponseEntity<ContentMapper> createContentMapper(@Valid @RequestBody ContentMapper contentMapper) throws URISyntaxException {
        log.debug("REST request to save ContentMapper : {}", contentMapper);
        if (contentMapper.getId() != null) {
            throw new BadRequestAlertException("A new contentMapper cannot already have an ID", ENTITY_NAME, "idexists");
        }
        ContentMapper result = contentMapperService.save(contentMapper);
        return ResponseEntity.created(new URI("/api/content-mappers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /content-mappers} : Updates an existing contentMapper.
     *
     * @param contentMapper the contentMapper to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated contentMapper,
     * or with status {@code 400 (Bad Request)} if the contentMapper is not valid,
     * or with status {@code 500 (Internal Server Error)} if the contentMapper couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/content-mappers")
    public ResponseEntity<ContentMapper> updateContentMapper(@Valid @RequestBody ContentMapper contentMapper) throws URISyntaxException {
        log.debug("REST request to update ContentMapper : {}", contentMapper);
        if (contentMapper.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        ContentMapper result = contentMapperService.save(contentMapper);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, contentMapper.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /content-mappers} : get all the contentMappers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of contentMappers in body.
     */
    @GetMapping("/content-mappers")
    public ResponseEntity<List<ContentMapper>> getAllContentMappers(Pageable pageable) {
        log.debug("REST request to get a page of ContentMappers");
        Page<ContentMapper> page = contentMapperService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /content-mappers/:id} : get the "id" contentMapper.
     *
     * @param id the id of the contentMapper to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the contentMapper, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/content-mappers/{id}")
    public ResponseEntity<ContentMapper> getContentMapper(@PathVariable Long id) {
        log.debug("REST request to get ContentMapper : {}", id);
        Optional<ContentMapper> contentMapper = contentMapperService.findOne(id);
        return ResponseUtil.wrapOrNotFound(contentMapper);
    }

    /**
     * {@code DELETE  /content-mappers/:id} : delete the "id" contentMapper.
     *
     * @param id the id of the contentMapper to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/content-mappers/{id}")
    public ResponseEntity<Void> deleteContentMapper(@PathVariable Long id) {
        log.debug("REST request to delete ContentMapper : {}", id);
        contentMapperService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
