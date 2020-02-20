import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IActionExecution } from 'app/shared/model/action-execution.model';

type EntityResponseType = HttpResponse<IActionExecution>;
type EntityArrayResponseType = HttpResponse<IActionExecution[]>;

@Injectable({ providedIn: 'root' })
export class ActionExecutionService {
  public resourceUrl = SERVER_API_URL + 'api/action-executions';

  constructor(protected http: HttpClient) {}

  create(actionExecution: IActionExecution): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(actionExecution);
    return this.http
      .post<IActionExecution>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(actionExecution: IActionExecution): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(actionExecution);
    return this.http
      .put<IActionExecution>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IActionExecution>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IActionExecution[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(actionExecution: IActionExecution): IActionExecution {
    const copy: IActionExecution = Object.assign({}, actionExecution, {
      executionStartTime:
        actionExecution.executionStartTime && actionExecution.executionStartTime.isValid()
          ? actionExecution.executionStartTime.toJSON()
          : undefined,
      executionEndTime:
        actionExecution.executionEndTime && actionExecution.executionEndTime.isValid()
          ? actionExecution.executionEndTime.toJSON()
          : undefined
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
      res.body.forEach((actionExecution: IActionExecution) => {
        actionExecution.executionStartTime = actionExecution.executionStartTime ? moment(actionExecution.executionStartTime) : undefined;
        actionExecution.executionEndTime = actionExecution.executionEndTime ? moment(actionExecution.executionEndTime) : undefined;
      });
    }
    return res;
  }
}
