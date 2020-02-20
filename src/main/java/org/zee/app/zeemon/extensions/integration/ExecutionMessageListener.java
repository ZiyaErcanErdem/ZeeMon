package org.zee.app.zeemon.extensions.integration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.annotation.Poller;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.Message;
import org.zee.app.zeemon.extensions.execution.ExecutionEngine;

@MessageEndpoint
public class ExecutionMessageListener {
	
	private final Logger log = LoggerFactory.getLogger(ExecutionMessageListener.class);
	
	private final ExecutionEngine executionEngine;
	
	public ExecutionMessageListener(
			ExecutionEngine executionEngine
	) {
		this.executionEngine = executionEngine;
	}
	
	@ServiceActivator(inputChannel = "actionExecutionInputChannel", poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll="10"))
    public void listenActionsToExecute(Message<Long> message) {
        
		Long actionId = message.getPayload();
		
		this.executionEngine.prepareAndExecuteSqlAction(actionId);
    }
	
    @ServiceActivator(inputChannel = "taskExecutionInputChannel", poller = @Poller(fixedDelay = "1000", maxMessagesPerPoll="50"))
    public void listenTasksToExecute(Message<Long> message) {
    	        
    	Long taskId = message.getPayload();
    	
    	this.executionEngine.prepareAndExecuteSqlTask(taskId);
    	
    }

}
