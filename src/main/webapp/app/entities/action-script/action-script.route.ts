import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IActionScript, ActionScript } from 'app/shared/model/action-script.model';
import { ActionScriptService } from './action-script.service';
import { ActionScriptComponent } from './action-script.component';
import { ActionScriptDetailComponent } from './action-script-detail.component';
import { ActionScriptUpdateComponent } from './action-script-update.component';

@Injectable({ providedIn: 'root' })
export class ActionScriptResolve implements Resolve<IActionScript> {
  constructor(private service: ActionScriptService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IActionScript> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((actionScript: HttpResponse<ActionScript>) => {
          if (actionScript.body) {
            return of(actionScript.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ActionScript());
  }
}

export const actionScriptRoute: Routes = [
  {
    path: '',
    component: ActionScriptComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zeemonApp.actionScript.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ActionScriptDetailComponent,
    resolve: {
      actionScript: ActionScriptResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.actionScript.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ActionScriptUpdateComponent,
    resolve: {
      actionScript: ActionScriptResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.actionScript.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ActionScriptUpdateComponent,
    resolve: {
      actionScript: ActionScriptResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.actionScript.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
