import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { ITaskExecution } from 'app/shared/model/task-execution.model';

type EntityResponseType = HttpResponse<ITaskExecution>;
type EntityArrayResponseType = HttpResponse<ITaskExecution[]>;

@Injectable({ providedIn: 'root' })
export class TaskExecutionService {
  public resourceUrl = SERVER_API_URL + 'api/task-executions';

  constructor(protected http: HttpClient) {}

  create(taskExecution: ITaskExecution): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskExecution);
    return this.http
      .post<ITaskExecution>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(taskExecution: ITaskExecution): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(taskExecution);
    return this.http
      .put<ITaskExecution>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<ITaskExecution>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<ITaskExecution[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(taskExecution: ITaskExecution): ITaskExecution {
    const copy: ITaskExecution = Object.assign({}, taskExecution, {
      executionStartTime:
        taskExecution.executionStartTime && taskExecution.executionStartTime.isValid()
          ? taskExecution.executionStartTime.toJSON()
          : undefined,
      executionEndTime:
        taskExecution.executionEndTime && taskExecution.executionEndTime.isValid() ? taskExecution.executionEndTime.toJSON() : undefined
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
      res.body.forEach((taskExecution: ITaskExecution) => {
        taskExecution.executionStartTime = taskExecution.executionStartTime ? moment(taskExecution.executionStartTime) : undefined;
        taskExecution.executionEndTime = taskExecution.executionEndTime ? moment(taskExecution.executionEndTime) : undefined;
      });
    }
    return res;
  }
}
