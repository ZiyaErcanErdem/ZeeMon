import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ICheckScript } from 'app/shared/model/check-script.model';

type EntityResponseType = HttpResponse<ICheckScript>;
type EntityArrayResponseType = HttpResponse<ICheckScript[]>;

@Injectable({ providedIn: 'root' })
export class CheckScriptService {
  public resourceUrl = SERVER_API_URL + 'api/check-scripts';

  constructor(protected http: HttpClient) {}

  create(checkScript: ICheckScript): Observable<EntityResponseType> {
    return this.http.post<ICheckScript>(this.resourceUrl, checkScript, { observe: 'response' });
  }

  update(checkScript: ICheckScript): Observable<EntityResponseType> {
    return this.http.put<ICheckScript>(this.resourceUrl, checkScript, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<ICheckScript>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<ICheckScript[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
