import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import * as moment from 'moment';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContent } from 'app/shared/model/content.model';

type EntityResponseType = HttpResponse<IContent>;
type EntityArrayResponseType = HttpResponse<IContent[]>;

@Injectable({ providedIn: 'root' })
export class ContentService {
  public resourceUrl = SERVER_API_URL + 'api/contents';

  constructor(protected http: HttpClient) {}

  create(content: IContent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(content);
    return this.http
      .post<IContent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  update(content: IContent): Observable<EntityResponseType> {
    const copy = this.convertDateFromClient(content);
    return this.http
      .put<IContent>(this.resourceUrl, copy, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http
      .get<IContent>(`${this.resourceUrl}/${id}`, { observe: 'response' })
      .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http
      .get<IContent[]>(this.resourceUrl, { params: options, observe: 'response' })
      .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  protected convertDateFromClient(content: IContent): IContent {
    const copy: IContent = Object.assign({}, content, {
      date1: content.date1 && content.date1.isValid() ? content.date1.toJSON() : undefined,
      date2: content.date2 && content.date2.isValid() ? content.date2.toJSON() : undefined,
      date3: content.date3 && content.date3.isValid() ? content.date3.toJSON() : undefined,
      date4: content.date4 && content.date4.isValid() ? content.date4.toJSON() : undefined,
      date5: content.date5 && content.date5.isValid() ? content.date5.toJSON() : undefined,
      date6: content.date6 && content.date6.isValid() ? content.date6.toJSON() : undefined,
      date7: content.date7 && content.date7.isValid() ? content.date7.toJSON() : undefined,
      date8: content.date8 && content.date8.isValid() ? content.date8.toJSON() : undefined,
      date9: content.date9 && content.date9.isValid() ? content.date9.toJSON() : undefined,
      date10: content.date10 && content.date10.isValid() ? content.date10.toJSON() : undefined,
      bool1: content.bool1 && content.bool1.isValid() ? content.bool1.toJSON() : undefined,
      bool2: content.bool2 && content.bool2.isValid() ? content.bool2.toJSON() : undefined,
      bool3: content.bool3 && content.bool3.isValid() ? content.bool3.toJSON() : undefined,
      bool4: content.bool4 && content.bool4.isValid() ? content.bool4.toJSON() : undefined,
      bool5: content.bool5 && content.bool5.isValid() ? content.bool5.toJSON() : undefined
    });
    return copy;
  }

  protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
    if (res.body) {
      res.body.date1 = res.body.date1 ? moment(res.body.date1) : undefined;
      res.body.date2 = res.body.date2 ? moment(res.body.date2) : undefined;
      res.body.date3 = res.body.date3 ? moment(res.body.date3) : undefined;
      res.body.date4 = res.body.date4 ? moment(res.body.date4) : undefined;
      res.body.date5 = res.body.date5 ? moment(res.body.date5) : undefined;
      res.body.date6 = res.body.date6 ? moment(res.body.date6) : undefined;
      res.body.date7 = res.body.date7 ? moment(res.body.date7) : undefined;
      res.body.date8 = res.body.date8 ? moment(res.body.date8) : undefined;
      res.body.date9 = res.body.date9 ? moment(res.body.date9) : undefined;
      res.body.date10 = res.body.date10 ? moment(res.body.date10) : undefined;
      res.body.bool1 = res.body.bool1 ? moment(res.body.bool1) : undefined;
      res.body.bool2 = res.body.bool2 ? moment(res.body.bool2) : undefined;
      res.body.bool3 = res.body.bool3 ? moment(res.body.bool3) : undefined;
      res.body.bool4 = res.body.bool4 ? moment(res.body.bool4) : undefined;
      res.body.bool5 = res.body.bool5 ? moment(res.body.bool5) : undefined;
    }
    return res;
  }

  protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
    if (res.body) {
      res.body.forEach((content: IContent) => {
        content.date1 = content.date1 ? moment(content.date1) : undefined;
        content.date2 = content.date2 ? moment(content.date2) : undefined;
        content.date3 = content.date3 ? moment(content.date3) : undefined;
        content.date4 = content.date4 ? moment(content.date4) : undefined;
        content.date5 = content.date5 ? moment(content.date5) : undefined;
        content.date6 = content.date6 ? moment(content.date6) : undefined;
        content.date7 = content.date7 ? moment(content.date7) : undefined;
        content.date8 = content.date8 ? moment(content.date8) : undefined;
        content.date9 = content.date9 ? moment(content.date9) : undefined;
        content.date10 = content.date10 ? moment(content.date10) : undefined;
        content.bool1 = content.bool1 ? moment(content.bool1) : undefined;
        content.bool2 = content.bool2 ? moment(content.bool2) : undefined;
        content.bool3 = content.bool3 ? moment(content.bool3) : undefined;
        content.bool4 = content.bool4 ? moment(content.bool4) : undefined;
        content.bool5 = content.bool5 ? moment(content.bool5) : undefined;
      });
    }
    return res;
  }
}
