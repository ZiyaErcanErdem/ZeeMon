import { Moment } from 'moment';
import { IActionExecution } from 'app/shared/model/action-execution.model';
import { IAgent } from 'app/shared/model/agent.model';
import { IActionScript } from 'app/shared/model/action-script.model';
import { ActionState } from 'app/shared/model/enumerations/action-state.model';

export interface IAction {
  id?: number;
  actionName?: string;
  actionDescription?: string;
  nextExecutionStartTime?: Moment;
  actionState?: ActionState;
  resolutionRuleExpression?: string;
  actionExecutions?: IActionExecution[];
  agent?: IAgent;
  actionScript?: IActionScript;
}

export class Action implements IAction {
  constructor(
    public id?: number,
    public actionName?: string,
    public actionDescription?: string,
    public nextExecutionStartTime?: Moment,
    public actionState?: ActionState,
    public resolutionRuleExpression?: string,
    public actionExecutions?: IActionExecution[],
    public agent?: IAgent,
    public actionScript?: IActionScript
  ) {}
}
