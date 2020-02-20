import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IFieldMapping, FieldMapping } from 'app/shared/model/field-mapping.model';
import { FieldMappingService } from './field-mapping.service';
import { FieldMappingComponent } from './field-mapping.component';
import { FieldMappingDetailComponent } from './field-mapping-detail.component';
import { FieldMappingUpdateComponent } from './field-mapping-update.component';

@Injectable({ providedIn: 'root' })
export class FieldMappingResolve implements Resolve<IFieldMapping> {
  constructor(private service: FieldMappingService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IFieldMapping> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((fieldMapping: HttpResponse<FieldMapping>) => {
          if (fieldMapping.body) {
            return of(fieldMapping.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new FieldMapping());
  }
}

export const fieldMappingRoute: Routes = [
  {
    path: '',
    component: FieldMappingComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zeemonApp.fieldMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: FieldMappingDetailComponent,
    resolve: {
      fieldMapping: FieldMappingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.fieldMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: FieldMappingUpdateComponent,
    resolve: {
      fieldMapping: FieldMappingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.fieldMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: FieldMappingUpdateComponent,
    resolve: {
      fieldMapping: FieldMappingResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.fieldMapping.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
