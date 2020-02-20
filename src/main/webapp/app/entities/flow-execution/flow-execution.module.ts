import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { FlowExecutionComponent } from './flow-execution.component';
import { FlowExecutionDetailComponent } from './flow-execution-detail.component';
import { FlowExecutionUpdateComponent } from './flow-execution-update.component';
import { FlowExecutionDeleteDialogComponent } from './flow-execution-delete-dialog.component';
import { flowExecutionRoute } from './flow-execution.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(flowExecutionRoute)],
  declarations: [FlowExecutionComponent, FlowExecutionDetailComponent, FlowExecutionUpdateComponent, FlowExecutionDeleteDialogComponent],
  entryComponents: [FlowExecutionDeleteDialogComponent]
})
export class ZeemonFlowExecutionModule {}
