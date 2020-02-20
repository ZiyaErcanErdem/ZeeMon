import { Moment } from 'moment';
import { ITaskExecution } from 'app/shared/model/task-execution.model';
import { IContent } from 'app/shared/model/content.model';
import { IFlow } from 'app/shared/model/flow.model';
import { ExecutionStatus } from 'app/shared/model/enumerations/execution-status.model';

export interface IFlowExecution {
  id?: number;
  executionStartTime?: Moment;
  executionEndTime?: Moment;
  totalTaskCount?: number;
  runningTaskCount?: number;
  errorTaskCount?: number;
  executionStatus?: ExecutionStatus;
  taskExecutions?: ITaskExecution[];
  contents?: IContent[];
  flow?: IFlow;
}

export class FlowExecution implements IFlowExecution {
  constructor(
    public id?: number,
    public executionStartTime?: Moment,
    public executionEndTime?: Moment,
    public totalTaskCount?: number,
    public runningTaskCount?: number,
    public errorTaskCount?: number,
    public executionStatus?: ExecutionStatus,
    public taskExecutions?: ITaskExecution[],
    public contents?: IContent[],
    public flow?: IFlow
  ) {}
}
