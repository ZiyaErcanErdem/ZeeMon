import { IEndpointProperty } from 'app/shared/model/endpoint-property.model';
import { ICheckScript } from 'app/shared/model/check-script.model';
import { IActionScript } from 'app/shared/model/action-script.model';
import { EndpointType } from 'app/shared/model/enumerations/endpoint-type.model';
import { EndpointSpec } from 'app/shared/model/enumerations/endpoint-spec.model';

export interface IEndpoint {
  id?: number;
  endpointName?: string;
  endpointInstanceId?: string;
  endpointType?: EndpointType;
  endpointSpec?: EndpointSpec;
  endpointDescription?: string;
  endpointProperties?: IEndpointProperty[];
  checkScripts?: ICheckScript[];
  actionScripts?: IActionScript[];
}

export class Endpoint implements IEndpoint {
  constructor(
    public id?: number,
    public endpointName?: string,
    public endpointInstanceId?: string,
    public endpointType?: EndpointType,
    public endpointSpec?: EndpointSpec,
    public endpointDescription?: string,
    public endpointProperties?: IEndpointProperty[],
    public checkScripts?: ICheckScript[],
    public actionScripts?: IActionScript[]
  ) {}
}
