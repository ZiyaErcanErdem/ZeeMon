import { IEndpoint } from 'app/shared/model/endpoint.model';
import { PropKeyType } from 'app/shared/model/enumerations/prop-key-type.model';
import { DataType } from 'app/shared/model/enumerations/data-type.model';

export interface IEndpointProperty {
  id?: number;
  propKey?: string;
  propKeyType?: PropKeyType;
  propValue?: string;
  propValueType?: DataType;
  propDescription?: string;
  endpoint?: IEndpoint;
}

export class EndpointProperty implements IEndpointProperty {
  constructor(
    public id?: number,
    public propKey?: string,
    public propKeyType?: PropKeyType,
    public propValue?: string,
    public propValueType?: DataType,
    public propDescription?: string,
    public endpoint?: IEndpoint
  ) {}
}
