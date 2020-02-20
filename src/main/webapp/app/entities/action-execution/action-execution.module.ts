import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { ActionExecutionComponent } from './action-execution.component';
import { ActionExecutionDetailComponent } from './action-execution-detail.component';
import { ActionExecutionUpdateComponent } from './action-execution-update.component';
import { ActionExecutionDeleteDialogComponent } from './action-execution-delete-dialog.component';
import { actionExecutionRoute } from './action-execution.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(actionExecutionRoute)],
  declarations: [
    ActionExecutionComponent,
    ActionExecutionDetailComponent,
    ActionExecutionUpdateComponent,
    ActionExecutionDeleteDialogComponent
  ],
  entryComponents: [ActionExecutionDeleteDialogComponent]
})
export class ZeemonActionExecutionModule {}
