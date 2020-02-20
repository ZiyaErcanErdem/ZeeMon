import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, Routes, Router } from '@angular/router';
import { JhiResolvePagingParams } from 'ng-jhipster';
import { Observable, of, EMPTY } from 'rxjs';
import { flatMap } from 'rxjs/operators';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { ITaskExecution, TaskExecution } from 'app/shared/model/task-execution.model';
import { TaskExecutionService } from './task-execution.service';
import { TaskExecutionComponent } from './task-execution.component';
import { TaskExecutionDetailComponent } from './task-execution-detail.component';
import { TaskExecutionUpdateComponent } from './task-execution-update.component';

@Injectable({ providedIn: 'root' })
export class TaskExecutionResolve implements Resolve<ITaskExecution> {
  constructor(private service: TaskExecutionService, private router: Router) {}

  resolve(route: ActivatedRouteSnapshot): Observable<ITaskExecution> | Observable<never> {
    const id = route.params['id'];
    if (id) {
      return this.service.find(id).pipe(
        flatMap((taskExecution: HttpResponse<TaskExecution>) => {
          if (taskExecution.body) {
            return of(taskExecution.body);
          } else {
            this.router.navigate(['404']);
            return EMPTY;
          }
        })
      );
    }
    return of(new TaskExecution());
  }
}

export const taskExecutionRoute: Routes = [
  {
    path: '',
    component: TaskExecutionComponent,
    resolve: {
      pagingParams: JhiResolvePagingParams
    },
    data: {
      authorities: ['ROLE_USER'],
      defaultSort: 'id,asc',
      pageTitle: 'zeemonApp.taskExecution.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/view',
    component: TaskExecutionDetailComponent,
    resolve: {
      taskExecution: TaskExecutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.taskExecution.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: 'new',
    component: TaskExecutionUpdateComponent,
    resolve: {
      taskExecution: TaskExecutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.taskExecution.home.title'
    },
    canActivate: [UserRouteAccessService]
  },
  {
    path: ':id/edit',
    component: TaskExecutionUpdateComponent,
    resolve: {
      taskExecution: TaskExecutionResolve
    },
    data: {
      authorities: ['ROLE_USER'],
      pageTitle: 'zeemonApp.taskExecution.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];
