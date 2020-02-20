import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IFlowExecution } from 'app/shared/model/flow-execution.model';

type EntityResponseType = HttpResponse<IFlowExecution>;
type EntityArrayResponseType = HttpResponse<IFlowExecution[]>;

@Injectable({ providedIn: 'root' })
export class FlowExecutionService {
  public resourceUrl = SERVER_API_URL + 'api/flow-executions';

  constructor(protected http: HttpClient) {}

  create(flowExecution: IFlowExecution): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(flowExecution);
    return this.http
      .post<IFlowExecution>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(flowExecution: IFlowExecution): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(flowExecution);
    return this.http
      .put<IFlowExecution>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IFlowExecution>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IFlowExecution[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(flowExecution: IFlowExecution): IFlowExecution {
    const copy: IFlowExecution = Object.assign({}, flowExecution, {
      executionStartTime:
        flowExecution.executionStartTime && flowExecution.executionStartTime.isValid()
          ? flowExecution.executionStartTime.toJSON()
          : undefined,
      executionEndTime:
        flowExecution.executionEndTime && flowExecution.executionEndTime.isValid() ? flowExecution.executionEndTime.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.executionStartTime = res.body.executionStartTime ? moment(res.body.executionStartTime) : undefined;
      res.body.executionEndTime = res.body.executionEndTime ? moment(res.body.executionEndTime) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((flowExecution: IFlowExecution) => {
        flowExecution.executionStartTime = flowExecution.executionStartTime ? moment(flowExecution.executionStartTime) : undefined;
        flowExecution.executionEndTime = flowExecution.executionEndTime ? moment(flowExecution.executionEndTime) : undefined;
      });
    }
    return res;
  }
}
