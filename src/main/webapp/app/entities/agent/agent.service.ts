import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAgent } from 'app/shared/model/agent.model';

type EntityResponseType = HttpResponse<IAgent>;
type EntityArrayResponseType = HttpResponse<IAgent[]>;

@Injectable({ providedIn: 'root' })
export class AgentService {
  public resourceUrl = SERVER_API_URL + 'api/agents';

  constructor(protected http: HttpClient) {}

  create(agent: IAgent): Observable<EntityResponseType> {
    return this.http.post<IAgent>(this.resourceUrl, agent, { observe: 'response' });
  }

  update(agent: IAgent): Observable<EntityResponseType> {
    return this.http.put<IAgent>(this.resourceUrl, agent, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IAgent>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IAgent[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
