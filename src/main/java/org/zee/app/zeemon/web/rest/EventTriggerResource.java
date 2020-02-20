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
import org.zee.app.zeemon.domain.EventTrigger;
import org.zee.app.zeemon.service.EventTriggerService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.EventTrigger}.
 */
@RestController
@RequestMapping("/api")
public class EventTriggerResource {

    private final Logger log = LoggerFactory.getLogger(EventTriggerResource.class);

    private static final String ENTITY_NAME = "eventTrigger";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final EventTriggerService eventTriggerService;

    public EventTriggerResource(EventTriggerService eventTriggerService) {
        this.eventTriggerService = eventTriggerService;
    }

    /**
     * {@code POST  /event-triggers} : Create a new eventTrigger.
     *
     * @param eventTrigger the eventTrigger to create.
     * @return the {@link ResponseEntity} with status {@code 201 (Created)} and with body the new eventTrigger, or with status {@code 400 (Bad Request)} if the eventTrigger has already an ID.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PostMapping("/event-triggers")
    public ResponseEntity<EventTrigger> createEventTrigger(@Valid @RequestBody EventTrigger eventTrigger) throws URISyntaxException {
        log.debug("REST request to save EventTrigger : {}", eventTrigger);
        if (eventTrigger.getId() != null) {
            throw new BadRequestAlertException("A new eventTrigger cannot already have an ID", ENTITY_NAME, "idexists");
        }
        EventTrigger result = eventTriggerService.save(eventTrigger);
        return ResponseEntity.created(new URI("/api/event-triggers/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * {@code PUT  /event-triggers} : Updates an existing eventTrigger.
     *
     * @param eventTrigger the eventTrigger to update.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the updated eventTrigger,
     * or with status {@code 400 (Bad Request)} if the eventTrigger is not valid,
     * or with status {@code 500 (Internal Server Error)} if the eventTrigger couldn't be updated.
     * @throws URISyntaxException if the Location URI syntax is incorrect.
     */
    @PutMapping("/event-triggers")
    public ResponseEntity<EventTrigger> updateEventTrigger(@Valid @RequestBody EventTrigger eventTrigger) throws URISyntaxException {
        log.debug("REST request to update EventTrigger : {}", eventTrigger);
        if (eventTrigger.getId() == null) {
            throw new BadRequestAlertException("Invalid id", ENTITY_NAME, "idnull");
        }
        EventTrigger result = eventTriggerService.save(eventTrigger);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(applicationName, true, ENTITY_NAME, eventTrigger.getId().toString()))
            .body(result);
    }

    /**
     * {@code GET  /event-triggers} : get all the eventTriggers.
     *
     * @param pageable the pagination information.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and the list of eventTriggers in body.
     */
    @GetMapping("/event-triggers")
    public ResponseEntity<List<EventTrigger>> getAllEventTriggers(Pageable pageable) {
        log.debug("REST request to get a page of EventTriggers");
        Page<EventTrigger> page = eventTriggerService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(ServletUriComponentsBuilder.fromCurrentRequest(), page);
        return ResponseEntity.ok().headers(headers).body(page.getContent());
    }

    /**
     * {@code GET  /event-triggers/:id} : get the "id" eventTrigger.
     *
     * @param id the id of the eventTrigger to retrieve.
     * @return the {@link ResponseEntity} with status {@code 200 (OK)} and with body the eventTrigger, or with status {@code 404 (Not Found)}.
     */
    @GetMapping("/event-triggers/{id}")
    public ResponseEntity<EventTrigger> getEventTrigger(@PathVariable Long id) {
        log.debug("REST request to get EventTrigger : {}", id);
        Optional<EventTrigger> eventTrigger = eventTriggerService.findOne(id);
        return ResponseUtil.wrapOrNotFound(eventTrigger);
    }

    /**
     * {@code DELETE  /event-triggers/:id} : delete the "id" eventTrigger.
     *
     * @param id the id of the eventTrigger to delete.
     * @return the {@link ResponseEntity} with status {@code 204 (NO_CONTENT)}.
     */
    @DeleteMapping("/event-triggers/{id}")
    public ResponseEntity<Void> deleteEventTrigger(@PathVariable Long id) {
        log.debug("REST request to delete EventTrigger : {}", id);
        eventTriggerService.delete(id);
        return ResponseEntity.noContent().headers(HeaderUtil.createEntityDeletionAlert(applicationName, true, ENTITY_NAME, id.toString())).build();
    }
}
