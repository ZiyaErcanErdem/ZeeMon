import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFlowExecution, FlowExecution } from 'app/shared/model/flow-execution.model';
import { FlowExecutionService } from './flow-execution.service';
import { FlowExecutionComponent } from './flow-execution.component';
import { FlowExecutionDetailComponent } from './flow-execution-detail.component';
import { FlowExecutionUpdateComponent } from './flow-execution-update.component';

@Injectable({ providedIn: 'root' })
export class FlowExecutionResolve implements Resolve<IFlowExecution> {
  constructor(private service: FlowExecutionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFlowExecution> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((flowExecution: HttpResponse<FlowExecution>) => {
          if (flowExecution.body) {
            return of(flowExecution.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FlowExecution());
  }
}

export const flowExecutionRoute: Routes = [
  {
    path: '',
    component: FlowExecutionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zeemonApp.flowExecution.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FlowExecutionDetailComponent,
    resolve: {
      flowExecution: FlowExecutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.flowExecution.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FlowExecutionUpdateComponent,
    resolve: {
      flowExecution: FlowExecutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.flowExecution.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FlowExecutionUpdateComponent,
    resolve: {
      flowExecution: FlowExecutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.flowExecution.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
