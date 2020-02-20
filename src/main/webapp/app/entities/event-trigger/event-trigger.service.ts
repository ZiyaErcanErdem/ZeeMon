import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IEventTrigger } from 'app/shared/model/event-trigger.model';

type EntityResponseType = HttpResponse<IEventTrigger>;
type EntityArrayResponseType = HttpResponse<IEventTrigger[]>;

@Injectable({ providedIn: 'root' })
export class EventTriggerService {
  public resourceUrl = SERVER_API_URL + 'api/event-triggers';

  constructor(protected http: HttpClient) {}

  create(eventTrigger: IEventTrigger): Observable<EntityResponseType> {
    return this.http.post<IEventTrigger>(this.resourceUrl, eventTrigger, { observe: 'response' });
  }

  update(eventTrigger: IEventTrigger): Observable<EntityResponseType> {
    return this.http.put<IEventTrigger>(this.resourceUrl, eventTrigger, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IEventTrigger>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IEventTrigger[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
