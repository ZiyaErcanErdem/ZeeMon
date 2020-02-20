import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IScriptParam, ScriptParam } from 'app/shared/model/script-param.model';
import { ScriptParamService } from './script-param.service';
import { ScriptParamComponent } from './script-param.component';
import { ScriptParamDetailComponent } from './script-param-detail.component';
import { ScriptParamUpdateComponent } from './script-param-update.component';

@Injectable({ providedIn: 'root' })
export class ScriptParamResolve implements Resolve<IScriptParam> {
  constructor(private service: ScriptParamService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IScriptParam> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((scriptParam: HttpResponse<ScriptParam>) => {
          if (scriptParam.body) {
            return of(scriptParam.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ScriptParam());
  }
}

export const scriptParamRoute: Routes = [
  {
    path: '',
    component: ScriptParamComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zeemonApp.scriptParam.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ScriptParamDetailComponent,
    resolve: {
      scriptParam: ScriptParamResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.scriptParam.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ScriptParamUpdateComponent,
    resolve: {
      scriptParam: ScriptParamResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.scriptParam.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ScriptParamUpdateComponent,
    resolve: {
      scriptParam: ScriptParamResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.scriptParam.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
