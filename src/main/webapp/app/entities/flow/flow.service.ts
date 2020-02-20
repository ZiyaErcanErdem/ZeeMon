import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFlow } from 'app/shared/model/flow.model';

type EntityResponseType = HttpResponse<IFlow>;
type EntityArrayResponseType = HttpResponse<IFlow[]>;

@Injectable({ providedIn: 'root' })
export class FlowService {
  public resourceUrl = SERVER_API_URL + 'api/flows';

  constructor(protected http: HttpClient) {}

  create(flow: IFlow): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(flow);
    return this.http
      .post<IFlow>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(flow: IFlow): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(flow);
    return this.http
      .put<IFlow>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFlow>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFlow[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(flow: IFlow): IFlow {
    const copy: IFlow = Object.assign({}, flow, {
      nextExecutionStartTime:
        flow.nextExecutionStartTime && flow.nextExecutionStartTime.isValid() ? flow.nextExecutionStartTime.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.nextExecutionStartTime = res.body.nextExecutionStartTime ? moment(res.body.nextExecutionStartTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((flow: IFlow) => {
        flow.nextExecutionStartTime = flow.nextExecutionStartTime ? moment(flow.nextExecutionStartTime) : undefined;
      });
    }
    return res;
  }
}
