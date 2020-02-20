import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFieldMapping } from 'app/shared/model/field-mapping.model';

type EntityResponseType = HttpResponse<IFieldMapping>;
type EntityArrayResponseType = HttpResponse<IFieldMapping[]>;

@Injectable({ providedIn: 'root' })
export class FieldMappingService {
  public resourceUrl = SERVER_API_URL + 'api/field-mappings';

  constructor(protected http: HttpClient) {}

  create(fieldMapping: IFieldMapping): Observable<EntityResponseType> {
    return this.http.post<IFieldMapping>(this.resourceUrl, fieldMapping, { observe: 'response' });
  }

  update(fieldMapping: IFieldMapping): Observable<EntityResponseType> {
    return this.http.put<IFieldMapping>(this.resourceUrl, fieldMapping, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IFieldMapping>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IFieldMapping[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
