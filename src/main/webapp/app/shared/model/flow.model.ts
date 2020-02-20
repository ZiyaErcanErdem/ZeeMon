import { Moment } from 'moment';
import { IFlowExecution } from 'app/shared/model/flow-execution.model';
import { ITask } from 'app/shared/model/task.model';
import { IContent } from 'app/shared/model/content.model';
import { IEventTrigger } from 'app/shared/model/event-trigger.model';
import { FlowState } from 'app/shared/model/enumerations/flow-state.model';

export interface IFlow {
  id?: number;
  flowName?: string;
  flowDescription?: string;
  nextExecutionStartTime?: Moment;
  flowState?: FlowState;
  flowExecutions?: IFlowExecution[];
  tasks?: ITask[];
  contents?: IContent[];
  eventTrigger?: IEventTrigger;
}

export class Flow implements IFlow {
  constructor(
    public id?: number,
    public flowName?: string,
    public flowDescription?: string,
    public nextExecutionStartTime?: Moment,
    public flowState?: FlowState,
    public flowExecutions?: IFlowExecution[],
    public tasks?: ITask[],
    public contents?: IContent[],
    public eventTrigger?: IEventTrigger
  ) {}
}
