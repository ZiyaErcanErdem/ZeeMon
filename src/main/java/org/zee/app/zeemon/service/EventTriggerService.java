package org.zee.app.zeemon.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.EventTrigger;
import org.zee.app.zeemon.repository.EventTriggerRepository;

import java.util.Optional;

/**
 * Service Implementation for managing {@link EventTrigger}.
 */
@Service
@Transactional
public class EventTriggerService {

    private final Logger log = LoggerFactory.getLogger(EventTriggerService.class);

    private final EventTriggerRepository eventTriggerRepository;

    public EventTriggerService(EventTriggerRepository eventTriggerRepository) {
        this.eventTriggerRepository = eventTriggerRepository;
    }

    /**
     * Save a eventTrigger.
     *
     * @param eventTrigger the entity to save.
     * @return the persisted entity.
     */
    public EventTrigger save(EventTrigger eventTrigger) {
        log.debug("Request to save EventTrigger : {}", eventTrigger);
        return eventTriggerRepository.save(eventTrigger);
    }

    /**
     * Get all the eventTriggers.
     *
     * @param pageable the pagination information.
     * @return the list of entities.
     */
    @Transactional(readOnly = true)
    public Page<EventTrigger> findAll(Pageable pageable) {
        log.debug("Request to get all EventTriggers");
        return eventTriggerRepository.findAll(pageable);
    }

    /**
     * Get one eventTrigger by id.
     *
     * @param id the id of the entity.
     * @return the entity.
     */
    @Transactional(readOnly = true)
    public Optional<EventTrigger> findOne(Long id) {
        log.debug("Request to get EventTrigger : {}", id);
        return eventTriggerRepository.findById(id);
    }

    /**
     * Delete the eventTrigger by id.
     *
     * @param id the id of the entity.
     */
    public void delete(Long id) {
        log.debug("Request to delete EventTrigger : {}", id);
        eventTriggerRepository.deleteById(id);
    }
}
