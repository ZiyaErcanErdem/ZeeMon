import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEndpoint } from 'app/shared/model/endpoint.model';

type EntityResponseType = HttpResponse<IEndpoint>;
type EntityArrayResponseType = HttpResponse<IEndpoint[]>;

@Injectable({ providedIn: 'root' })
export class EndpointService {
  public resourceUrl = SERVER_API_URL + 'api/endpoints';

  constructor(protected http: HttpClient) {}

  create(endpoint: IEndpoint): Observable<EntityResponseType> {
    return this.http.post<IEndpoint>(this.resourceUrl, endpoint, { observe: 'response' });
  }

  update(endpoint: IEndpoint): Observable<EntityResponseType> {
    return this.http.put<IEndpoint>(this.resourceUrl, endpoint, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEndpoint>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEndpoint[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
