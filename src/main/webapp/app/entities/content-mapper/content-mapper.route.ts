import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IContentMapper, ContentMapper } from 'app/shared/model/content-mapper.model';
import { ContentMapperService } from './content-mapper.service';
import { ContentMapperComponent } from './content-mapper.component';
import { ContentMapperDetailComponent } from './content-mapper-detail.component';
import { ContentMapperUpdateComponent } from './content-mapper-update.component';

@Injectable({ providedIn: 'root' })
export class ContentMapperResolve implements Resolve<IContentMapper> {
  constructor(private service: ContentMapperService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IContentMapper> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((contentMapper: HttpResponse<ContentMapper>) => {
          if (contentMapper.body) {
            return of(contentMapper.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new ContentMapper());
  }
}

export const contentMapperRoute: Routes = [
  {
    path: '',
    component: ContentMapperComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zeemonApp.contentMapper.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: ContentMapperDetailComponent,
    resolve: {
      contentMapper: ContentMapperResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.contentMapper.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: ContentMapperUpdateComponent,
    resolve: {
      contentMapper: ContentMapperResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.contentMapper.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: ContentMapperUpdateComponent,
    resolve: {
      contentMapper: ContentMapperResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.contentMapper.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
