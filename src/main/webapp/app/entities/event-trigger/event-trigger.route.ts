import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IEventTrigger, EventTrigger } from 'app/shared/model/event-trigger.model';
import { EventTriggerService } from './event-trigger.service';
import { EventTriggerComponent } from './event-trigger.component';
import { EventTriggerDetailComponent } from './event-trigger-detail.component';
import { EventTriggerUpdateComponent } from './event-trigger-update.component';

@Injectable({ providedIn: 'root' })
export class EventTriggerResolve implements Resolve<IEventTrigger> {
  constructor(private service: EventTriggerService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IEventTrigger> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((eventTrigger: HttpResponse<EventTrigger>) => {
          if (eventTrigger.body) {
            return of(eventTrigger.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new EventTrigger());
  }
}

export const eventTriggerRoute: Routes = [
  {
    path: '',
    component: EventTriggerComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zeemonApp.eventTrigger.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: EventTriggerDetailComponent,
    resolve: {
      eventTrigger: EventTriggerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.eventTrigger.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: EventTriggerUpdateComponent,
    resolve: {
      eventTrigger: EventTriggerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.eventTrigger.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: EventTriggerUpdateComponent,
    resolve: {
      eventTrigger: EventTriggerResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.eventTrigger.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
