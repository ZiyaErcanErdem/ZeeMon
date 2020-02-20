package org.zee.app.zeemon.extensions.rest;

import java.net.URI;
import java.net.URISyntaxException;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zee.app.zeemon.domain.ContentMapper;
import org.zee.app.zeemon.extensions.execution.ExecutionEngine;
import org.zee.app.zeemon.extensions.execution.model.ExecutionResultRequest;
import org.zee.app.zeemon.extensions.execution.model.dto.ExecutionResultDto;
import org.zee.app.zeemon.extensions.parser.MapperService;
import org.zee.app.zeemon.web.rest.errors.BadRequestAlertException;

import io.github.jhipster.web.util.HeaderUtil;

/**
 * REST controller for managing {@link org.zee.app.zeemon.domain.CheckScript}.
 */
@RestController
@RequestMapping("/api")
public class ZeemonResource {

    private final Logger log = LoggerFactory.getLogger(ZeemonResource.class);

    private static final String ENTITY_NAME = "ContentMapper";

    @Value("${jhipster.clientApp.name}")
    private String applicationName;

    private final MapperService mapperService;
    private final ExecutionEngine executionEngine;

    public ZeemonResource(
    		MapperService mapperService,
    		ExecutionEngine executionEngine
    ) {
        this.mapperService = mapperService;
        this.executionEngine = executionEngine;
    }

    @PostMapping("/generate-sql-mapper/{scriptId}")
    public ResponseEntity<ContentMapper> generateSqlMapper(@PathVariable Long scriptId) throws URISyntaxException {
        log.debug("REST request to generate ContentMapper CheckScript.id : {}", scriptId);
        if (scriptId == null || scriptId.longValue() <= 0) {
            throw new BadRequestAlertException("CheckScript.id must be greater than 0", ENTITY_NAME, "invalid_script_id");
        }
        ContentMapper result = mapperService.generateSqlMapperForCheckScript(scriptId);
        return ResponseEntity.created(new URI("/api/generate-sql-mapper/content-mapper/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.getId().toString()))
            .body(result);
    }
    
    @PostMapping("/generate-sql-params/{scriptId}")
    public ResponseEntity<Boolean> generateSqlParams(@PathVariable Long scriptId) throws URISyntaxException {
        log.debug("REST request to generate SqlParams CheckScript.id : {}", scriptId);
        if (scriptId == null || scriptId.longValue() <= 0) {
            throw new BadRequestAlertException("CheckScript.id must be greater than 0", ENTITY_NAME, "invalid_script_id");
        }
        Boolean result = mapperService.generateSqlParamsForCheckScript(scriptId);
        return ResponseEntity.created(new URI("/api/generate-sql-params/content-mapper/" + result))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.toString()))
            .body(result);
    }
    
    @PostMapping("/execute-sql-task/{taskId}")
    public ResponseEntity<Boolean> executeSqlTask(@PathVariable Long taskId) throws URISyntaxException {
        log.debug("REST request to execute SQL Task - Task.id : {}", taskId);
        if (taskId == null || taskId.longValue() <= 0) {
            throw new BadRequestAlertException("Task.id must be greater than 0", ENTITY_NAME, "invalid_task_id");
        }
        Boolean result = this.executionEngine.prepareAndExecuteSqlTask(taskId);
        return ResponseEntity.created(new URI("/api/execute-sql-task/" + result))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.toString()))
            .body(result);
    }
    
    @PostMapping("/execute-sql-action/{actionId}")
    public ResponseEntity<Boolean> executeSqlAction(@PathVariable Long actionId) throws URISyntaxException {
        log.debug("REST request to test Action Action.id : {}", actionId);
        if (actionId == null || actionId.longValue() <= 0) {
            throw new BadRequestAlertException("actionId.id must be greater than 0", ENTITY_NAME, "invalid_action_id");
        }
        Boolean result = this.executionEngine.prepareAndExecuteSqlAction(actionId);
        return ResponseEntity.created(new URI("/api/execute-sql-action/" + result))
            .headers(HeaderUtil.createEntityCreationAlert(applicationName, true, ENTITY_NAME, result.toString()))
            .body(result);
    }
    
    @PostMapping("/executionResult/view")
    public ResponseEntity<ExecutionResultDto> prepareExecutionResult(@Valid @RequestBody ExecutionResultRequest request) throws URISyntaxException {
        log.debug("REST request to prepare ExecutionResult for request: {}", request);
        ExecutionResultDto result = this.executionEngine.prepareExecutionResultDto(request);
        return ResponseEntity.ok(result);
    }
}

