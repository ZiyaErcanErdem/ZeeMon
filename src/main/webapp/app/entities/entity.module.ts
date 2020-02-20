import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'agent',
        loadChildren: () => import('./agent/agent.module').then(m => m.ZeemonAgentModule)
      },
      {
        path: 'endpoint-property',
        loadChildren: () => import('./endpoint-property/endpoint-property.module').then(m => m.ZeemonEndpointPropertyModule)
      },
      {
        path: 'endpoint',
        loadChildren: () => import('./endpoint/endpoint.module').then(m => m.ZeemonEndpointModule)
      },
      {
        path: 'flow-execution',
        loadChildren: () => import('./flow-execution/flow-execution.module').then(m => m.ZeemonFlowExecutionModule)
      },
      {
        path: 'task-execution',
        loadChildren: () => import('./task-execution/task-execution.module').then(m => m.ZeemonTaskExecutionModule)
      },
      {
        path: 'action-execution',
        loadChildren: () => import('./action-execution/action-execution.module').then(m => m.ZeemonActionExecutionModule)
      },
      {
        path: 'action-param',
        loadChildren: () => import('./action-param/action-param.module').then(m => m.ZeemonActionParamModule)
      },
      {
        path: 'action-script',
        loadChildren: () => import('./action-script/action-script.module').then(m => m.ZeemonActionScriptModule)
      },
      {
        path: 'event-trigger',
        loadChildren: () => import('./event-trigger/event-trigger.module').then(m => m.ZeemonEventTriggerModule)
      },
      {
        path: 'flow',
        loadChildren: () => import('./flow/flow.module').then(m => m.ZeemonFlowModule)
      },
      {
        path: 'task',
        loadChildren: () => import('./task/task.module').then(m => m.ZeemonTaskModule)
      },
      {
        path: 'action',
        loadChildren: () => import('./action/action.module').then(m => m.ZeemonActionModule)
      },
      {
        path: 'script-param',
        loadChildren: () => import('./script-param/script-param.module').then(m => m.ZeemonScriptParamModule)
      },
      {
        path: 'check-script',
        loadChildren: () => import('./check-script/check-script.module').then(m => m.ZeemonCheckScriptModule)
      },
      {
        path: 'content-mapper',
        loadChildren: () => import('./content-mapper/content-mapper.module').then(m => m.ZeemonContentMapperModule)
      },
      {
        path: 'field-mapping',
        loadChildren: () => import('./field-mapping/field-mapping.module').then(m => m.ZeemonFieldMappingModule)
      },
      {
        path: 'content',
        loadChildren: () => import('./content/content.module').then(m => m.ZeemonContentModule)
      },
      {
        path: 'content-validation-error',
        loadChildren: () =>
          import('./content-validation-error/content-validation-error.module').then(m => m.ZeemonContentValidationErrorModule)
      }
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ])
  ]
})
export class ZeemonEntityModule {}
