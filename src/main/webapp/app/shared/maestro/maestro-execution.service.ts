import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { SERVER_API_URL } from 'app/app.constants';

import { JhiDateUtils, JhiParseLinks } from 'ng-jhipster';
import { IExecutionResult } from './model/execution-result.model';
import { ExecutionResultRequest } from './model/execution-result-request.model';
import { DynamicService } from 'angular-dynamic-page';

@Injectable({
  providedIn: 'root'
})
export class MaestroExecutionService {
  private microserviceName? = '';

  constructor(
    private dynamicService: DynamicService,
    private http: HttpClient,
    private linkParser: JhiParseLinks,
    public dateUtils: JhiDateUtils
  ) {
    this.microserviceName = this.dynamicService.getConfig().microserviceName;
  }

  private apiUriOf(path: string): string {
    if (this.microserviceName) {
      return `${SERVER_API_URL}services/${this.microserviceName}/api/${path}`;
    } else {
      return `${SERVER_API_URL}/api/${path}`;
    }
  }

  public prepareExecutionResult(ctx: ExecutionResultRequest): Observable<HttpResponse<IExecutionResult>> {
    const requestURI = this.apiUriOf(`executionResult/view`);
    return this.http
      .post<IExecutionResult>(requestURI, ctx, { observe: 'response' })
      .pipe(map((res: HttpResponse<IExecutionResult>) => this.convertDateFromServerForExecutionResult(res)));
  }

  private convertDateFromServerForExecutionResult(res: HttpResponse<IExecutionResult>): HttpResponse<IExecutionResult> {
    // res.body.evaluationDate = res.body.evaluationDate != null ? moment(res.body.evaluationDate) : null;
    // res.body.firstReleaseDate = res.body.firstReleaseDate != null ? moment(res.body.firstReleaseDate) : null;
    return res;
  }
}
