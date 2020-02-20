import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IActionExecution, ActionExecution } from 'app/shared/model/action-execution.model';
import { ActionExecutionService } from './action-execution.service';
import { ActionExecutionComponent } from './action-execution.component';
import { ActionExecutionDetailComponent } from './action-execution-detail.component';
import { ActionExecutionUpdateComponent } from './action-execution-update.component';

@Injectable({ providedIn: 'root' })
export class ActionExecutionResolve implements Resolve<IActionExecution> {
  constructor(private service: ActionExecutionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActionExecution> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((actionExecution: HttpResponse<ActionExecution>) => {
          if (actionExecution.body) {
            return of(actionExecution.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ActionExecution());
  }
}

export const actionExecutionRoute: Routes = [
  {
    path: '',
    component: ActionExecutionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zeemonApp.actionExecution.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ActionExecutionDetailComponent,
    resolve: {
      actionExecution: ActionExecutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.actionExecution.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ActionExecutionUpdateComponent,
    resolve: {
      actionExecution: ActionExecutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.actionExecution.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ActionExecutionUpdateComponent,
    resolve: {
      actionExecution: ActionExecutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.actionExecution.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
