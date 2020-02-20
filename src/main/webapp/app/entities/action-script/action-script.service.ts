import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IActionScript } from 'app/shared/model/action-script.model';

type EntityResponseType = HttpResponse<IActionScript>;
type EntityArrayResponseType = HttpResponse<IActionScript[]>;

@Injectable({ providedIn: 'root' })
export class ActionScriptService {
  public resourceUrl = SERVER_API_URL + 'api/action-scripts';

  constructor(protected http: HttpClient) {}

  create(actionScript: IActionScript): Observable<EntityResponseType> {
    return this.http.post<IActionScript>(this.resourceUrl, actionScript, { observe: 'response' });
  }

  update(actionScript: IActionScript): Observable<EntityResponseType> {
    return this.http.put<IActionScript>(this.resourceUrl, actionScript, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IActionScript>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IActionScript[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
