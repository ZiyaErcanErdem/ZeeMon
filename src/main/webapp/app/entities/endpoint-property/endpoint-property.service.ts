import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEndpointProperty } from 'app/shared/model/endpoint-property.model';

type EntityResponseType = HttpResponse<IEndpointProperty>;
type EntityArrayResponseType = HttpResponse<IEndpointProperty[]>;

@Injectable({ providedIn: 'root' })
export class EndpointPropertyService {
  public resourceUrl = SERVER_API_URL + 'api/endpoint-properties';

  constructor(protected http: HttpClient) {}

  create(endpointProperty: IEndpointProperty): Observable<EntityResponseType> {
    return this.http.post<IEndpointProperty>(this.resourceUrl, endpointProperty, { observe: 'response' });
  }

  update(endpointProperty: IEndpointProperty): Observable<EntityResponseType> {
    return this.http.put<IEndpointProperty>(this.resourceUrl, endpointProperty, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEndpointProperty>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEndpointProperty[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
