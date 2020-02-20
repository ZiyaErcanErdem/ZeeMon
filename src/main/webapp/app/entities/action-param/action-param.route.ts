import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IActionParam, ActionParam } from 'app/shared/model/action-param.model';
import { ActionParamService } from './action-param.service';
import { ActionParamComponent } from './action-param.component';
import { ActionParamDetailComponent } from './action-param-detail.component';
import { ActionParamUpdateComponent } from './action-param-update.component';

@Injectable({ providedIn: 'root' })
export class ActionParamResolve implements Resolve<IActionParam> {
  constructor(private service: ActionParamService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActionParam> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((actionParam: HttpResponse<ActionParam>) => {
          if (actionParam.body) {
            return of(actionParam.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ActionParam());
  }
}

export const actionParamRoute: Routes = [
  {
    path: '',
    component: ActionParamComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zeemonApp.actionParam.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ActionParamDetailComponent,
    resolve: {
      actionParam: ActionParamResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.actionParam.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ActionParamUpdateComponent,
    resolve: {
      actionParam: ActionParamResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.actionParam.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ActionParamUpdateComponent,
    resolve: {
      actionParam: ActionParamResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.actionParam.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
