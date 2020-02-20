import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { UserRouteAccessService } from 'app/core/auth/user-route-access-service';
import { PagePagingParamsResolver } from 'app/pages/page-paging-params-resolver';

import { PageAgentComponent } from './page-agent.component';
import { DynamicPageModule } from 'angular-dynamic-page';

export const ENTITY_STATES: Routes = [
  {
    path: '',
    component: PageAgentComponent,
    resolve: {
      pagingParams: PagePagingParamsResolver
    },
    data: {
      authorities: ['ROLE_ADMIN', 'ROLE_USER'],
      pageTitle: 'zeemonApp.agent.home.title'
    },
    canActivate: [UserRouteAccessService]
  }
];

@NgModule({
  imports: [DynamicPageModule, RouterModule.forChild(ENTITY_STATES)],
  declarations: [PageAgentComponent],
  providers: [PagePagingParamsResolver],
  entryComponents: [PageAgentComponent]
})
export class ZeemonPageAgentModule {}
