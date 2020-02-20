import { IScriptParam } from 'app/shared/model/script-param.model';
import { ITask } from 'app/shared/model/task.model';
import { IContent } from 'app/shared/model/content.model';
import { IEndpoint } from 'app/shared/model/endpoint.model';
import { IContentMapper } from 'app/shared/model/content-mapper.model';
import { ScriptType } from 'app/shared/model/enumerations/script-type.model';

export interface ICheckScript {
  id?: number;
  scriptName?: string;
  scriptType?: ScriptType;
  scriptSource?: string;
  actionRuleExpression?: string;
  scriptParams?: IScriptParam[];
  tasks?: ITask[];
  contents?: IContent[];
  endpoint?: IEndpoint;
  contentMapper?: IContentMapper;
}

export class CheckScript implements ICheckScript {
  constructor(
    public id?: number,
    public scriptName?: string,
    public scriptType?: ScriptType,
    public scriptSource?: string,
    public actionRuleExpression?: string,
    public scriptParams?: IScriptParam[],
    public tasks?: ITask[],
    public contents?: IContent[],
    public endpoint?: IEndpoint,
    public contentMapper?: IContentMapper
  ) {}
}
