import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { FlowComponent } from './flow.component';
import { FlowDetailComponent } from './flow-detail.component';
import { FlowUpdateComponent } from './flow-update.component';
import { FlowDeleteDialogComponent } from './flow-delete-dialog.component';
import { flowRoute } from './flow.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(flowRoute)],
  declarations: [FlowComponent, FlowDetailComponent, FlowUpdateComponent, FlowDeleteDialogComponent],
  entryComponents: [FlowDeleteDialogComponent]
})
export class ZeemonFlowModule {}
