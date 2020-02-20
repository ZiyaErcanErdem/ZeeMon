import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IScriptParam } from 'app/shared/model/script-param.model';

type EntityResponseType = HttpResponse<IScriptParam>;
type EntityArrayResponseType = HttpResponse<IScriptParam[]>;

@Injectable({ providedIn: 'root' })
export class ScriptParamService {
  public resourceUrl = SERVER_API_URL + 'api/script-params';

  constructor(protected http: HttpClient) {}

  create(scriptParam: IScriptParam): Observable<EntityResponseType> {
    return this.http.post<IScriptParam>(this.resourceUrl, scriptParam, { observe: 'response' });
  }

  update(scriptParam: IScriptParam): Observable<EntityResponseType> {
    return this.http.put<IScriptParam>(this.resourceUrl, scriptParam, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IScriptParam>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IScriptParam[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
