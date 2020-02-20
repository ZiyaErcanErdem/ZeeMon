import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';
import { PagePagingParamsResolver } from './page-paging-params-resolver';
import { MaestroModule } from 'app/shared/maestro/maestro.module';

@NgModule({
  imports: [
    MaestroModule,
    RouterModule.forChild([
      {
        path: 'page-agent',
        loadChildren: () => import('./page-agent/page-agent.module').then(m => m.ZeemonPageAgentModule)
      },
      {
        path: 'page-endpoint',
        loadChildren: () => import('./page-endpoint/page-endpoint.module').then(m => m.ZeemonPageEndpointModule)
      },
      {
        path: 'page-endpoint-property',
        loadChildren: () => import('./page-endpoint-property/page-endpoint-property.module').then(m => m.ZeemonPageEndpointPropertyModule)
      },
      {
        path: 'page-flow-execution',
        loadChildren: () => import('./page-flow-execution/page-flow-execution.module').then(m => m.ZeemonPageFlowExecutionModule)
      },
      {
        path: 'page-task-execution',
        loadChildren: () => import('./page-task-execution/page-task-execution.module').then(m => m.ZeemonPageTaskExecutionModule)
      },
      {
        path: 'page-action-execution',
        loadChildren: () => import('./page-action-execution/page-action-execution.module').then(m => m.ZeemonPageActionExecutionModule)
      },
      {
        path: 'page-action-param',
        loadChildren: () => import('./page-action-param/page-action-param.module').then(m => m.ZeemonPageActionParamModule)
      },
      {
        path: 'page-action-script',
        loadChildren: () => import('./page-action-script/page-action-script.module').then(m => m.ZeemonPageActionScriptModule)
      },
      {
        path: 'page-event-trigger',
        loadChildren: () => import('./page-event-trigger/page-event-trigger.module').then(m => m.ZeemonPageEventTriggerModule)
      },
      {
        path: 'page-flow',
        loadChildren: () => import('./page-flow/page-flow.module').then(m => m.ZeemonPageFlowModule)
      },
      {
        path: 'page-task',
        loadChildren: () => import('./page-task/page-task.module').then(m => m.ZeemonPageTaskModule)
      },
      {
        path: 'page-action',
        loadChildren: () => import('./page-action/page-action.module').then(m => m.ZeemonPageActionModule)
      },
      {
        path: 'page-script-param',
        loadChildren: () => import('./page-script-param/page-script-param.module').then(m => m.ZeemonPageScriptParamModule)
      },
      {
        path: 'page-check-script',
        loadChildren: () => import('./page-check-script/page-check-script.module').then(m => m.ZeemonPageCheckScriptModule)
      },
      {
        path: 'page-content-mapper',
        loadChildren: () => import('./page-content-mapper/page-content-mapper.module').then(m => m.ZeemonPageContentMapperModule)
      },
      {
        path: 'page-field-mapping',
        loadChildren: () => import('./page-field-mapping/page-field-mapping.module').then(m => m.ZeemonPageFieldMappingModule)
      },
      {
        path: 'page-content',
        loadChildren: () => import('./page-content/page-content.module').then(m => m.ZeemonPageContentModule)
      },
      {
        path: 'page-content-validation-error',
        loadChildren: () =>
          import('./page-content-validation-error/page-content-validation-error.module').then(
            m => m.ZeemonPageContentValidationErrorModule
          )
      }
    ])
  ],
  providers: [PagePagingParamsResolver]
})
export class ZeemonPageModule {}
