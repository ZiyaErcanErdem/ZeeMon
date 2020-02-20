import { Moment } from 'moment';
import { IContent } from 'app/shared/model/content.model';
import { IContentValidationError } from 'app/shared/model/content-validation-error.model';
import { ITask } from 'app/shared/model/task.model';
import { IFlowExecution } from 'app/shared/model/flow-execution.model';
import { ExecutionStatus } from 'app/shared/model/enumerations/execution-status.model';

export interface ITaskExecution {
  id?: number;
  executionStartTime?: Moment;
  executionEndTime?: Moment;
  executionStatus?: ExecutionStatus;
  contents?: IContent[];
  contentValidationErrors?: IContentValidationError[];
  task?: ITask;
  flowExecution?: IFlowExecution;
}

export class TaskExecution implements ITaskExecution {
  constructor(
    public id?: number,
    public executionStartTime?: Moment,
    public executionEndTime?: Moment,
    public executionStatus?: ExecutionStatus,
    public contents?: IContent[],
    public contentValidationErrors?: IContentValidationError[],
    public task?: ITask,
    public flowExecution?: IFlowExecution
  ) {}
}
