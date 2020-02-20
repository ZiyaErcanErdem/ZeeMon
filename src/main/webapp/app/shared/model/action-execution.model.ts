import { Moment } from 'moment';
import { IAction } from 'app/shared/model/action.model';
import { ExecutionStatus } from 'app/shared/model/enumerations/execution-status.model';

export interface IActionExecution {
  id?: number;
  executionStartTime?: Moment;
  executionEndTime?: Moment;
  executionStatus?: ExecutionStatus;
  actionLog?: string;
  action?: IAction;
}

export class ActionExecution implements IActionExecution {
  constructor(
    public id?: number,
    public executionStartTime?: Moment,
    public executionEndTime?: Moment,
    public executionStatus?: ExecutionStatus,
    public actionLog?: string,
    public action?: IAction
  ) {}
}
