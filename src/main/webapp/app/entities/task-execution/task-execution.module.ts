import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { TaskExecutionComponent } from './task-execution.component';
import { TaskExecutionDetailComponent } from './task-execution-detail.component';
import { TaskExecutionUpdateComponent } from './task-execution-update.component';
import { TaskExecutionDeleteDialogComponent } from './task-execution-delete-dialog.component';
import { taskExecutionRoute } from './task-execution.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(taskExecutionRoute)],
  declarations: [TaskExecutionComponent, TaskExecutionDetailComponent, TaskExecutionUpdateComponent, TaskExecutionDeleteDialogComponent],
  entryComponents: [TaskExecutionDeleteDialogComponent]
})
export class ZeemonTaskExecutionModule {}
