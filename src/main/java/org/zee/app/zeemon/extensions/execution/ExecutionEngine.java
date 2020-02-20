package org.zee.app.zeemon.extensions.execution;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.annotation.MessageEndpoint;
import org.springframework.integration.support.MessageBuilder;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.zee.app.zeemon.domain.Action;
import org.zee.app.zeemon.domain.ActionExecution;
import org.zee.app.zeemon.domain.ActionParam;
import org.zee.app.zeemon.domain.ActionScript;
import org.zee.app.zeemon.domain.Agent;
import org.zee.app.zeemon.domain.CheckScript;
import org.zee.app.zeemon.domain.Content;
import org.zee.app.zeemon.domain.ContentMapper;
import org.zee.app.zeemon.domain.Endpoint;
import org.zee.app.zeemon.domain.EndpointProperty;
import org.zee.app.zeemon.domain.EventTrigger;
import org.zee.app.zeemon.domain.FieldMapping;
import org.zee.app.zeemon.domain.Flow;
import org.zee.app.zeemon.domain.FlowExecution;
import org.zee.app.zeemon.domain.ScriptParam;
import org.zee.app.zeemon.domain.Task;
import org.zee.app.zeemon.domain.TaskExecution;
import org.zee.app.zeemon.domain.enumeration.ActionState;
import org.zee.app.zeemon.domain.enumeration.ExecutionStatus;
import org.zee.app.zeemon.domain.enumeration.FlowState;
import org.zee.app.zeemon.domain.enumeration.TaskState;
import org.zee.app.zeemon.extensions.execution.mapper.ExecutionResultMapper;
import org.zee.app.zeemon.extensions.execution.model.ExecutionResult;
import org.zee.app.zeemon.extensions.execution.model.ExecutionResultRequest;
import org.zee.app.zeemon.extensions.execution.model.dto.ExecutionResultDto;
import org.zee.app.zeemon.extensions.expression.ZeemonUtils;
import org.zee.app.zeemon.repository.ActionExecutionRepository;
import org.zee.app.zeemon.repository.ActionRepository;
import org.zee.app.zeemon.repository.ContentRepository;
import org.zee.app.zeemon.repository.FlowExecutionRepository;
import org.zee.app.zeemon.repository.FlowRepository;
import org.zee.app.zeemon.repository.TaskExecutionRepository;
import org.zee.app.zeemon.repository.TaskRepository;

@MessageEndpoint
@Service
@Transactional
public class ExecutionEngine {
	private final Logger log = LoggerFactory.getLogger(ExecutionEngine.class);
	
	private final DatabaseScriptExecuter databaseScriptExecuter;
	private final FlowRepository flowRepository;
	private final TaskRepository taskRepository;
    private final FlowExecutionRepository flowExecutionRepository;
    private final TaskExecutionRepository taskExecutionRepository;
    private final ContentRepository contentRepository;
    
    private final ActionRepository actionRepository;
    private final ActionExecutionRepository actionExecutionRepository;
    
    private final MessageChannel taskExecutionInputChannel;
    private final MessageChannel actionExecutionInputChannel;
	
	public ExecutionEngine(
			FlowRepository flowRepository,
			TaskRepository taskRepository,
			DatabaseScriptExecuter databaseScriptExecuter,
    		FlowExecutionRepository flowExecutionRepository,
    		TaskExecutionRepository taskExecutionRepository,
    		ContentRepository contentRepository,
    		ActionRepository actionRepository,
    		ActionExecutionRepository actionExecutionRepository,
    		
    		MessageChannel taskExecutionInputChannel,
    		MessageChannel actionExecutionInputChannel
	) {
		this.flowRepository = flowRepository;
		this.taskRepository = taskRepository;
		this.databaseScriptExecuter = databaseScriptExecuter;
		this.flowExecutionRepository = flowExecutionRepository;
        this.taskExecutionRepository = taskExecutionRepository;
        this.contentRepository = contentRepository;
        
        this.actionRepository = actionRepository;
        this.actionExecutionRepository = actionExecutionRepository;
        
        this.taskExecutionInputChannel = taskExecutionInputChannel;
        this.actionExecutionInputChannel = actionExecutionInputChannel;
	}
	
	public void checkAndExecutePendingFlows() {
		
		int MAX_HOURS = 720; // 30 Days
		FlowState flowState = FlowState.PENDING;
		Instant lookupEndTime = Instant.now();
		Instant lookupStartTime = lookupEndTime.minus(MAX_HOURS, ChronoUnit.HOURS);
		
		List<Flow> flows = this.flowRepository.findAllByFlowStateAndNextExecutionStartTimeBetween(flowState, lookupStartTime, lookupEndTime);
		
		if (null == flows || flows.isEmpty()) {
			return;
		}
		
		for(Flow flow : flows) {
			
			Set<Task> tasksInFlow = flow.getTasks();
			
			EventTrigger eventTrigger = flow.getEventTrigger();
			Instant nextExecutionTime = flow.getNextExecutionStartTime();
			
			Instant nextScheculeTime = ZeemonUtils.parseNextTime(eventTrigger, nextExecutionTime);
			flow.setNextExecutionStartTime(nextScheculeTime);
			flow.setFlowState(FlowState.PROCESSING);
			
			if (null != tasksInFlow && !tasksInFlow.isEmpty()) {
				flow = this.flowRepository.save(flow);
			}
					
			
			if (null != tasksInFlow) {
				
				List<Task> tasks = new ArrayList<>(tasksInFlow);
				
				for(Task task : tasks) {
					this.checkAndExecutePendingTask(flow, task, eventTrigger);
				}
			}
			
			flow.setFlowState(FlowState.PENDING);
			flow = this.flowRepository.save(flow);
		}
		
	}
	
	public void checkAndExecutePendingActions() {
		
		int MAX_HOURS = 48; // 2 Days
		ActionState actionState = ActionState.PENDING;
		Instant lookupEndTime = Instant.now();
		Instant lookupStartTime = lookupEndTime.minus(MAX_HOURS, ChronoUnit.HOURS);
		
		List<Action> actions = this.actionRepository.findAllByActionStateAndNextExecutionStartTimeBetween(actionState, lookupStartTime, lookupEndTime);
		
		if (null == actions || actions.isEmpty()) {
			return;
		}
		
		for(Action action : actions) {		
			this.processAction(action);
		}
		
	}
	
	private void checkAndExecutePendingTask(Flow flow, Task task, EventTrigger eventTrigger) {
		if (null == task) {
			return;
		}
		
		TaskState taskState = task.getTaskState();
		
		if (TaskState.PENDING != taskState) {
			return;
		}
		
		Instant nextExecutionTime = task.getNextExecutionStartTime();
		Instant now = Instant.now();
		
		if (null != nextExecutionTime) {
	        int result = now.compareTo(nextExecutionTime); 
	        
	        if (result < 0) {
	        	return;
	        }
		}

		this.processTask(flow, task);
		
	}

	private void processTask(Flow flow, Task task) {
		log.info("Processing task : {}", task);
		
		if (null == task) {
			return;
		}
		
		Long taskId = task.getId();
		
		
		Message<Long> message = MessageBuilder.withPayload(taskId)
                .setHeader("ExecutionType", "TASK")
                .build();
		
		this.taskExecutionInputChannel.send(message);
		
		// this.prepareAndExecuteSqlTask(taskId);
	}
	
	private void processAction(Action action) {
		log.info("Processing action : {}", action);
		
		if (null == action) {
			return;
		}
		
		Long actionId = action.getId();
		
		Message<Long> message = MessageBuilder.withPayload(actionId)
				.setHeader("ExecutionType", "ACTION")
                .build();
		
		this.actionExecutionInputChannel.send(message);
		
		// this.prepareAndExecuteSqlAction(actionId);
	}
	
    public boolean prepareAndExecuteSqlAction(Long actionId) {
        
     	ActionScriptExecutionContext ctx = this.prepareActionScriptExecutionContext(actionId);
     	
        if (null == ctx) {
        	return false;
        }
        
        Action action = ctx.getAction();
        
        ActionExecution actionExecution = this.newExecution(action);
        ctx.setActionExecution(actionExecution);

    	boolean rc = this.executeAction(ctx);
    	log.debug("Action Execution Result [Action.id:{}] : {}", actionId, rc);
    	return true;
    	
    }
    
    public boolean prepareAndExecuteSqlTask(Long taskId) {
    	        
     	CheckScriptExecutionContext ctx = this.prepareCheckScriptExecutionContext(taskId);
     	
        if (null == ctx) {
        	return false;
        }
        
        Flow flow = ctx.getFlow();
        
        FlowExecution flowExecution = this.newExecution(flow);
        ctx.setFlowExecution(flowExecution);


    	boolean rc = this.executeScript(ctx);
    	if (rc) {
    		this.saveExecutionContent(ctx);
    		List<Content> contents = ctx.getContents();
    		if (null != contents) {
    			contents.forEach(c -> log.debug("SqlTask Execution Result [Task.id:{}] : {}", taskId, c));
    		}
    		
    		// this.contentRepository.saveAll(result);
    	}
    	return true;
    	
    }
	
	public boolean executeAction(ActionScriptExecutionContext ctx) {
		
		ActionExecution actionExecution = ctx.getActionExecution();
		Action action = ctx.getAction();
		

		action.setActionState(ActionState.PROCESSING);
		action = this.actionRepository.save(action);
		
        this.startExecution(ctx);
		boolean rc = this.databaseScriptExecuter.executeActionScript(ctx);
		
		ExecutionStatus executionStatus = rc ? ExecutionStatus.SUCCESS : ExecutionStatus.FAILED;
		this.finishExecution(ctx, executionStatus);
		
		ActionState actionState = rc ? ActionState.RESOLVED : ActionState.COMPLETED;
		action.setActionState(actionState);
		action = this.actionRepository.save(action);
		return rc;
	}
	
	
	public boolean executeScript(CheckScriptExecutionContext ctx) {
		
		FlowExecution flowExecution = ctx.getFlowExecution();
		Task task = ctx.getTask();
		EventTrigger eventTrigger = ctx.getEventTrigger();
		
		
		Instant nextExecutionTime = task.getNextExecutionStartTime();

		Instant nextScheculeTime = ZeemonUtils.parseNextTime(eventTrigger, nextExecutionTime);
		task.setNextExecutionStartTime(nextScheculeTime);
		task.setTaskState(TaskState.PROCESSING);
		
		task = this.taskRepository.save(task);
		
        TaskExecution taskExecution = this.newExecution(flowExecution, task);
        ctx.setTaskExecution(taskExecution);
        this.startExecution(ctx);
		boolean rc = this.databaseScriptExecuter.executeCheckScript(ctx);
		
		ExecutionStatus executionStatus = rc ? ExecutionStatus.SUCCESS : ExecutionStatus.FAILED;
		this.finishExecution(ctx, executionStatus);
		
		TaskState taskState = TaskState.PENDING;
		task.setTaskState(taskState);
		task = this.taskRepository.save(task);
		
		return rc;
	}
	
	private void startExecution(CheckScriptExecutionContext ctx) {
		TaskExecution taskExecution = ctx.getTaskExecution();
		taskExecution.setExecutionStartTime(Instant.now());
		taskExecution.setExecutionStatus(ExecutionStatus.EXECUTING);
		taskExecution = this.taskExecutionRepository.save(taskExecution);
		
		ctx.setTaskExecution(taskExecution);
	}
	
	private void finishExecution(CheckScriptExecutionContext ctx, ExecutionStatus executionStatus) {
		TaskExecution taskExecution = ctx.getTaskExecution();
		taskExecution.setExecutionEndTime(Instant.now());
		taskExecution.setExecutionStatus(executionStatus);
		taskExecution = this.taskExecutionRepository.save(taskExecution);
		
		ctx.setTaskExecution(taskExecution);
	}
	
	public boolean saveExecutionContent(ActionScriptExecutionContext ctx) {
		ActionExecution actionExecution = ctx.getActionExecution();
		Action action = ctx.getAction();
		String actionLog = actionExecution.getActionLog();
		
		ActionScript actionScript = ctx.getActionScript();

		return true;
	}
	
	public boolean saveExecutionContent(CheckScriptExecutionContext ctx) {
		FlowExecution flowExecution = ctx.getFlowExecution();
		Flow flow = ctx.getFlow();
		
		TaskExecution taskExecution = ctx.getTaskExecution();
		Task task = ctx.getTask();
		
		CheckScript checkScript = ctx.getCheckScript();
		
		List<Content> contents = ctx.getContents();
		
		if (null != contents) {
			contents.forEach(c -> {
				c.setCheckScript(checkScript);
				c.setFlow(flow);
				c.setTask(task);
				
				c.setFlowExecution(flowExecution);
				c.setTaskExecution(taskExecution);
			});
			
			contents = this.contentRepository.saveAll(contents);
			ctx.setContents(contents);			
		}
		return true;
	}
	
	private void startExecution(ActionScriptExecutionContext ctx) {
		ActionExecution actionExecution = ctx.getActionExecution();
		actionExecution.setExecutionStartTime(Instant.now());
		actionExecution.setExecutionStatus(ExecutionStatus.EXECUTING);
		actionExecution = this.actionExecutionRepository.save(actionExecution);
		
		ctx.setActionExecution(actionExecution);
	}
	
	private void finishExecution(ActionScriptExecutionContext ctx, ExecutionStatus executionStatus) {
		ActionExecution actionExecution = ctx.getActionExecution();
		actionExecution.setExecutionEndTime(Instant.now());
		actionExecution.setExecutionStatus(executionStatus);
		actionExecution = this.actionExecutionRepository.save(actionExecution);
		
		ctx.setActionExecution(actionExecution);
	}
	
	public ActionExecution newExecution(Action action) {
		
		if (null == action) {
			return null;
		}
		
		Instant now = Instant.now();
		
		ActionExecution ae = new ActionExecution();
		
		ae.setAction(action);
		ae.setActionLog(null);
		ae.setExecutionStartTime(now);
		ae.setExecutionEndTime(now);
		ae.setExecutionStatus(ExecutionStatus.PENDING);
		
		ae = this.actionExecutionRepository.save(ae);

		return ae;
	}
	
	public FlowExecution newExecution(Flow flow) {
		
		if (null == flow) {
			return null;
		}
		
		Instant now = Instant.now();
		
		FlowExecution fe = new FlowExecution();
		fe.setErrorTaskCount(0);
		fe.setExecutionEndTime(now);
		fe.setExecutionStartTime(now);
		fe.setExecutionStatus(ExecutionStatus.PENDING);
		fe.setFlow(flow);
		fe.setRunningTaskCount(0);
		fe.setTotalTaskCount(0);
		
        fe = this.flowExecutionRepository.save(fe);

		return fe;
	}
	
	private TaskExecution newExecution(FlowExecution flowExecution, Task task) {
		
		if (null == flowExecution || null == task) {
			return null;
		}
		
		Instant now = Instant.now();
		
		TaskExecution te = new TaskExecution();
		te.setExecutionEndTime(now);
		te.setExecutionStartTime(now);
		te.setExecutionStatus(ExecutionStatus.PENDING);
		te.setTask(task);
		te.setFlowExecution(flowExecution);
		
		te = this.taskExecutionRepository.save(te);
		
		return te;
	}
	
    public ActionScriptExecutionContext prepareActionScriptExecutionContext(Long actionId) {
    	log.debug("Request to prepare ActionScriptExecutionContext Action.id : {}", actionId);
    	
    	Action action = this.actionRepository.findById(actionId).map(d -> d).orElse(null);
        
        if (null == action) {
        	return null;
        }
        
        ActionScript actionScript = action.getActionScript();
        Agent agent = action.getAgent();
        Endpoint endpoint = actionScript.getEndpoint();
        Set<EndpointProperty> props = endpoint.getEndpointProperties();

        List<ActionParam> actionParams = null;
        List<EndpointProperty> endpointProperties = null;
        
        ActionScriptExecutionContext ctx = new ActionScriptExecutionContext();
        
        ctx.setAgent(agent);
        ctx.setEndpoint(endpoint);
        ctx.setAction(action);
        ctx.setActionScript(actionScript);

        
	   	 if(null != props) {
	   		endpointProperties = new ArrayList<>(props);
		 }  
	   	 
       
         Set<ActionParam> sourceParams =  actionScript.getActionParams();
         if(null != sourceParams) {
    		 actionParams = new ArrayList<>(sourceParams);
    	 }  

        ctx.setEndpointProperties(endpointProperties);
        ctx.setActionParams(actionParams);
       
        return ctx;        
    }
	
    public CheckScriptExecutionContext prepareCheckScriptExecutionContext(Long taskId) {
    	log.debug("Request to prepare CheckScriptExecutionContext Task.id : {}", taskId);
    	
        Task task = this.taskRepository.findById(taskId).map(d -> d).orElse(null);
        
        if (null == task) {
        	return null;
        }
        
        CheckScript checkScript = task.getCheckScript();
        ContentMapper contentMapper = checkScript.getContentMapper();
        Flow flow = task.getFlow();
        EventTrigger eventTrigger = flow.getEventTrigger();
        Agent agent = task.getAgent();
        Endpoint endpoint = checkScript.getEndpoint();
        Set<EndpointProperty> props = endpoint.getEndpointProperties();

        List<FieldMapping> fieldMappings = null;
        List<ScriptParam> scriptParams = null;
        List<EndpointProperty> endpointProperties = null;
        
        CheckScriptExecutionContext ctx = new CheckScriptExecutionContext();
        
        ctx.setAgent(agent);
        ctx.setEndpoint(endpoint);
        ctx.setEventTrigger(eventTrigger);
        ctx.setFlow(flow);
        ctx.setTask(task);
        ctx.setCheckScript(checkScript);
        ctx.setContentMapper(contentMapper);
        
	   	 if(null != props) {
	   		endpointProperties = new ArrayList<>(props);
		 }  
	   	 
    	 Set<FieldMapping> sourceMappings = contentMapper.getFieldMappings();
    	 if(null != sourceMappings) {
    		 fieldMappings = new ArrayList<>(sourceMappings);
    	 }
        
         Set<ScriptParam> sourceParams =  checkScript.getScriptParams();
         if(null != sourceParams) {
    		 scriptParams = new ArrayList<>(sourceParams);
    	 }  

        ctx.setEndpointProperties(endpointProperties);
        ctx.setFieldMappings(fieldMappings);
        ctx.setScriptParams(scriptParams);
       
        return ctx;        
    }

	public ExecutionResultDto prepareExecutionResultDto(@Valid ExecutionResultRequest request) {
		log.debug("Preparing ExecutionResultDto for request: {}", request);
		ExecutionResult ctx = this.prepareExecutionResult(request);
		ExecutionResultDto ctxDto = ExecutionResultMapper.MAPPER.toExecutionResultDto(ctx);
		return ctxDto;
	}
	
	public ExecutionResult prepareExecutionResult(ExecutionResultRequest request) {

		Long taskExecutionId = request.getTaskExecutionId();
		Boolean contentOnly = request.getContentOnly();

		ExecutionResult executionResult = new ExecutionResult();
		
		TaskExecution taskExecution = this.taskExecutionRepository.getOne(taskExecutionId);
		if (null == taskExecution) {
			return executionResult;
		}
		executionResult.setTaskExecution(taskExecution);
		
		Set<Content> executionContent = taskExecution.getContents();
		if (null != executionContent) {
			executionResult.setContent(new ArrayList<>(executionContent));
		}
		if (contentOnly) {
			return executionResult;
		}

		Task task = taskExecution.getTask();
		executionResult.setTask(task);
				
		FlowExecution flowExecution = taskExecution.getFlowExecution();
		executionResult.setFlowExecution(flowExecution);
		
		Flow flow = flowExecution.getFlow();
		executionResult.setFlow(flow);
		
		Agent agent = task.getAgent();
		executionResult.setAgent(agent);
		
		CheckScript checkScript = task.getCheckScript();
		
		ContentMapper contentMapper = checkScript.getContentMapper();
		if (null != contentMapper) {
			Set<FieldMapping> fieldMappings =  contentMapper.getFieldMappings();
			if (null != fieldMappings) {
				executionResult.setMappings(new ArrayList<>(fieldMappings));
			}
		}

		return executionResult;
	}

}
