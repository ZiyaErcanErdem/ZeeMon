import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ICheckScript, CheckScript } from 'app/shared/model/check-script.model';
import { CheckScriptService } from './check-script.service';
import { CheckScriptComponent } from './check-script.component';
import { CheckScriptDetailComponent } from './check-script-detail.component';
import { CheckScriptUpdateComponent } from './check-script-update.component';

@Injectable({ providedIn: 'root' })
export class CheckScriptResolve implements Resolve<ICheckScript> {
  constructor(private service: CheckScriptService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ICheckScript> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((checkScript: HttpResponse<CheckScript>) => {
          if (checkScript.body) {
            return of(checkScript.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new CheckScript());
  }
}

export const checkScriptRoute: Routes = [
  {
    path: '',
    component: CheckScriptComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zeemonApp.checkScript.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: CheckScriptDetailComponent,
    resolve: {
      checkScript: CheckScriptResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.checkScript.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: CheckScriptUpdateComponent,
    resolve: {
      checkScript: CheckScriptResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.checkScript.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: CheckScriptUpdateComponent,
    resolve: {
      checkScript: CheckScriptResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.checkScript.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
