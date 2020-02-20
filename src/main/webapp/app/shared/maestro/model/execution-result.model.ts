import { IAgent } from 'app/shared/model/agent.model';
import { IFlow } from 'app/shared/model/flow.model';
import { IFlowExecution } from 'app/shared/model/flow-execution.model';
import { ITask } from 'app/shared/model/task.model';
import { ITaskExecution } from 'app/shared/model/task-execution.model';
import { IFieldMapping } from 'app/shared/model/field-mapping.model';
import { IContent } from 'app/shared/model/content.model';

export interface IAgentDto extends IAgent {
}

export interface IFlowDto extends IFlow {
    eventTriggerId?: number;
}

export interface IFlowExecutionDto extends IFlowExecution {
    flowId?: number;
}

export interface ITaskDto extends ITask {
    agentId?: number;
    checkScriptId?: number;
    flowId?: number;
}

export interface ITaskExecutionDto extends ITaskExecution {
    taskId?: number;
    flowExecutionId?: number;
}

export interface IFieldMappingDto extends IFieldMapping {
    contentMapperId?: number;
}

export interface IContentDto extends IContent {
    flowId?: number;
    taskId?: number;
    flowExecutionId?: number;
    taskExecutionId?: number;
    checkScriptId?: number;
}


export interface IExecutionResult {
	agent?: IAgentDto;
	flow?: IFlowDto;
	flowExecution?: IFlowExecutionDto;
	task?: ITaskDto;
	taskExecution?: ITaskExecutionDto;
	mappings?: Array<IFieldMappingDto>;
	content?: Array<IContentDto>;
	
}

export class ExecutionResult implements IExecutionResult {
    constructor(
        public agent?: IAgentDto,
        public flow?: IFlowDto,
        public flowExecution?: IFlowExecutionDto,
        public task?: ITaskDto,
        public taskExecution?: ITaskExecutionDto,
        public mappings?: Array<IFieldMappingDto>,
        public content?: Array<IContentDto>,
    ) { }
}
