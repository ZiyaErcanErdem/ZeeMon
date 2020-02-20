import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContentMapper } from 'app/shared/model/content-mapper.model';

type EntityResponseType = HttpResponse<IContentMapper>;
type EntityArrayResponseType = HttpResponse<IContentMapper[]>;

@Injectable({ providedIn: 'root' })
export class ContentMapperService {
  public resourceUrl = SERVER_API_URL + 'api/content-mappers';

  constructor(protected http: HttpClient) {}

  create(contentMapper: IContentMapper): Observable<EntityResponseType> {
    return this.http.post<IContentMapper>(this.resourceUrl, contentMapper, { observe: 'response' });
  }

  update(contentMapper: IContentMapper): Observable<EntityResponseType> {
    return this.http.put<IContentMapper>(this.resourceUrl, contentMapper, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContentMapper>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContentMapper[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
