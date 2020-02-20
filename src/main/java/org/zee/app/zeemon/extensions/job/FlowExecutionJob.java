package org.zee.app.zeemon.extensions.job;

import java.time.Instant;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.zee.app.zeemon.extensions.execution.ExecutionEngine;


@Component
public class FlowExecutionJob {

	private final Logger log = LoggerFactory.getLogger(FlowExecutionJob.class);
	
	private final ExecutionEngine executionEngine;
	
	public FlowExecutionJob(ExecutionEngine executionEngine) {
		this.executionEngine = executionEngine;
	}
	
	@Scheduled(initialDelay = 60000, fixedDelay = 60000) // 1 min
    public void jobExecutePendingFlows() {
		log.debug("Checking flows to execute pending tasks - time: {}", Instant.now());
		this.executionEngine.checkAndExecutePendingFlows();
    }

	@Scheduled(initialDelay = 60000, fixedDelay = 120000) // 2 mins
    public void jobExecutePendingActions() {
		log.debug("Checking pending actions to execute - time: {}", Instant.now());
		this.executionEngine.checkAndExecutePendingActions();
    }
}
