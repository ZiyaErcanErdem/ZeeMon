import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IActionParam } from 'app/shared/model/action-param.model';

type EntityResponseType = HttpResponse<IActionParam>;
type EntityArrayResponseType = HttpResponse<IActionParam[]>;

@Injectable({ providedIn: 'root' })
export class ActionParamService {
  public resourceUrl = SERVER_API_URL + 'api/action-params';

  constructor(protected http: HttpClient) {}

  create(actionParam: IActionParam): Observable<EntityResponseType> {
    return this.http.post<IActionParam>(this.resourceUrl, actionParam, { observe: 'response' });
  }

  update(actionParam: IActionParam): Observable<EntityResponseType> {
    return this.http.put<IActionParam>(this.resourceUrl, actionParam, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IActionParam>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActionParam[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
