import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IAction } from 'app/shared/model/action.model';

type EntityResponseType = HttpResponse<IAction>;
type EntityArrayResponseType = HttpResponse<IAction[]>;

@Injectable({ providedIn: 'root' })
export class ActionService {
  public resourceUrl = SERVER_API_URL + 'api/actions';

  constructor(protected http: HttpClient) {}

  create(action: IAction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(action);
    return this.http
      .post<IAction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(action: IAction): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(action);
    return this.http
      .put<IAction>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IAction>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IAction[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(action: IAction): IAction {
    const copy: IAction = Object.assign({}, action, {
      nextExecutionStartTime:
        action.nextExecutionStartTime && action.nextExecutionStartTime.isValid() ? action.nextExecutionStartTime.toJSON() : undefined
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
      res.body.forEach((action: IAction) => {
        action.nextExecutionStartTime = action.nextExecutionStartTime ? moment(action.nextExecutionStartTime) : undefined;
      });
    }
    return res;
  }
}
