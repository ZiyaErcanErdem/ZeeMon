import { Moment } from 'moment';
import { ITaskExecution } from 'app/shared/model/task-execution.model';
import { IContent } from 'app/shared/model/content.model';
import { IContentValidationError } from 'app/shared/model/content-validation-error.model';
import { IAgent } from 'app/shared/model/agent.model';
import { ICheckScript } from 'app/shared/model/check-script.model';
import { IFlow } from 'app/shared/model/flow.model';
import { TaskState } from 'app/shared/model/enumerations/task-state.model';

export interface ITask {
  id?: number;
  taskName?: string;
  taskDescription?: string;
  nextExecutionStartTime?: Moment;
  taskState?: TaskState;
  taskExecutions?: ITaskExecution[];
  contents?: IContent[];
  contentValidationErrors?: IContentValidationError[];
  agent?: IAgent;
  checkScript?: ICheckScript;
  flow?: IFlow;
}

export class Task implements ITask {
  constructor(
    public id?: number,
    public taskName?: string,
    public taskDescription?: string,
    public nextExecutionStartTime?: Moment,
    public taskState?: TaskState,
    public taskExecutions?: ITaskExecution[],
    public contents?: IContent[],
    public contentValidationErrors?: IContentValidationError[],
    public agent?: IAgent,
    public checkScript?: ICheckScript,
    public flow?: IFlow
  ) {}
}
