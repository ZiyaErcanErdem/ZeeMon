import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContentValidationError, ContentValidationError } from 'app/shared/model/content-validation-error.model';
import { ContentValidationErrorService } from './content-validation-error.service';
import { ContentValidationErrorComponent } from './content-validation-error.component';
import { ContentValidationErrorDetailComponent } from './content-validation-error-detail.component';
import { ContentValidationErrorUpdateComponent } from './content-validation-error-update.component';

@Injectable({ providedIn: 'root' })
export class ContentValidationErrorResolve implements Resolve<IContentValidationError> {
  constructor(private service: ContentValidationErrorService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContentValidationError> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contentValidationError: HttpResponse<ContentValidationError>) => {
          if (contentValidationError.body) {
            return of(contentValidationError.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContentValidationError());
  }
}

export const contentValidationErrorRoute: Routes = [
  {
    path: '',
    component: ContentValidationErrorComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zeemonApp.contentValidationError.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContentValidationErrorDetailComponent,
    resolve: {
      contentValidationError: ContentValidationErrorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.contentValidationError.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContentValidationErrorUpdateComponent,
    resolve: {
      contentValidationError: ContentValidationErrorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.contentValidationError.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContentValidationErrorUpdateComponent,
    resolve: {
      contentValidationError: ContentValidationErrorResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.contentValidationError.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
