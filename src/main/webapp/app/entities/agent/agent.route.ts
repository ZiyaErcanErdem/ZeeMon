import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { IAgent, Agent } from 'app/shared/model/agent.model';
import { AgentService } from './agent.service';
import { AgentComponent } from './agent.component';
import { AgentDetailComponent } from './agent-detail.component';
import { AgentUpdateComponent } from './agent-update.component';

@Injectable({ providedIn: 'root' })
export class AgentResolve implements Resolve<IAgent> {
  constructor(private service: AgentService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<IAgent> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((agent: HttpResponse<Agent>) => {
          if (agent.body) {
            return of(agent.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new Agent());
  }
}

export const agentRoute: Routes = [
  {
    path: '',
    component: AgentComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zeemonApp.agent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: AgentDetailComponent,
    resolve: {
      agent: AgentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.agent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: AgentUpdateComponent,
    resolve: {
      agent: AgentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.agent.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: AgentUpdateComponent,
    resolve: {
      agent: AgentResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.agent.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
