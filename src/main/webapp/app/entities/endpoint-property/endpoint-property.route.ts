import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEndpointProperty, EndpointProperty } from 'app/shared/model/endpoint-property.model';
import { EndpointPropertyService } from './endpoint-property.service';
import { EndpointPropertyComponent } from './endpoint-property.component';
import { EndpointPropertyDetailComponent } from './endpoint-property-detail.component';
import { EndpointPropertyUpdateComponent } from './endpoint-property-update.component';

@Injectable({ providedIn: 'root' })
export class EndpointPropertyResolve implements Resolve<IEndpointProperty> {
  constructor(private service: EndpointPropertyService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEndpointProperty> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((endpointProperty: HttpResponse<EndpointProperty>) => {
          if (endpointProperty.body) {
            return of(endpointProperty.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EndpointProperty());
  }
}

export const endpointPropertyRoute: Routes = [
  {
    path: '',
    component: EndpointPropertyComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zeemonApp.endpointProperty.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EndpointPropertyDetailComponent,
    resolve: {
      endpointProperty: EndpointPropertyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.endpointProperty.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EndpointPropertyUpdateComponent,
    resolve: {
      endpointProperty: EndpointPropertyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.endpointProperty.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EndpointPropertyUpdateComponent,
    resolve: {
      endpointProperty: EndpointPropertyResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.endpointProperty.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
