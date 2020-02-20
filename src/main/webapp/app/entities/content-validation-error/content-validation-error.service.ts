import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared/util/request-util';
import { IContentValidationError } from 'app/shared/model/content-validation-error.model';

type EntityResponseType = HttpResponse<IContentValidationError>;
type EntityArrayResponseType = HttpResponse<IContentValidationError[]>;

@Injectable({ providedIn: 'root' })
export class ContentValidationErrorService {
  public resourceUrl = SERVER_API_URL + 'api/content-validation-errors';

  constructor(protected http: HttpClient) {}

  create(contentValidationError: IContentValidationError): Observable<EntityResponseType> {
    return this.http.post<IContentValidationError>(this.resourceUrl, contentValidationError, { observe: 'response' });
  }

  update(contentValidationError: IContentValidationError): Observable<EntityResponseType> {
    return this.http.put<IContentValidationError>(this.resourceUrl, contentValidationError, { observe: 'response' });
  }

  find(id: number): Observable<EntityResponseType> {
    return this.http.get<IContentValidationError>(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }

  query(req?: any): Observable<EntityArrayResponseType> {
    const options = createRequestOption(req);
    return this.http.get<IContentValidationError[]>(this.resourceUrl, { params: options, observe: 'response' });
  }

  delete(id: number): Observable<HttpResponse<{}>> {
    return this.http.delete(`${this.resourceUrl}/${id}`, { observe: 'response' });
  }
}
