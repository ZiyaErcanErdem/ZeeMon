import { ICheckScript } from 'app/shared/model/check-script.model';
import { IFieldMapping } from 'app/shared/model/field-mapping.model';
import { ItemFormat } from 'app/shared/model/enumerations/item-format.model';

export interface IContentMapper {
  id?: number;
  mapperName?: string;
  itemFormat?: ItemFormat;
  containsHeader?: boolean;
  fieldDelimiter?: string;
  checkScripts?: ICheckScript[];
  fieldMappings?: IFieldMapping[];
}

export class ContentMapper implements IContentMapper {
  constructor(
    public id?: number,
    public mapperName?: string,
    public itemFormat?: ItemFormat,
    public containsHeader?: boolean,
    public fieldDelimiter?: string,
    public checkScripts?: ICheckScript[],
    public fieldMappings?: IFieldMapping[]
  ) {
    this.containsHeader = this.containsHeader || false;
  }
}
