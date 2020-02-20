import { IActionParam } from 'app/shared/model/action-param.model';
import { IAction } from 'app/shared/model/action.model';
import { IEndpoint } from 'app/shared/model/endpoint.model';
import { ScriptType } from 'app/shared/model/enumerations/script-type.model';
import { ActionType } from 'app/shared/model/enumerations/action-type.model';

export interface IActionScript {
  id?: number;
  actionCode?: string;
  scriptName?: string;
  scriptType?: ScriptType;
  actionType?: ActionType;
  actionSource?: string;
  actionParams?: IActionParam[];
  actions?: IAction[];
  endpoint?: IEndpoint;
}

export class ActionScript implements IActionScript {
  constructor(
    public id?: number,
    public actionCode?: string,
    public scriptName?: string,
    public scriptType?: ScriptType,
    public actionType?: ActionType,
    public actionSource?: string,
    public actionParams?: IActionParam[],
    public actions?: IAction[],
    public endpoint?: IEndpoint
  ) {}
}
