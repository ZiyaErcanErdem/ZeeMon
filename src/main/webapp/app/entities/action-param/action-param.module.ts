import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { ActionParamComponent } from './action-param.component';
import { ActionParamDetailComponent } from './action-param-detail.component';
import { ActionParamUpdateComponent } from './action-param-update.component';
import { ActionParamDeleteDialogComponent } from './action-param-delete-dialog.component';
import { actionParamRoute } from './action-param.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(actionParamRoute)],
  declarations: [ActionParamComponent, ActionParamDetailComponent, ActionParamUpdateComponent, ActionParamDeleteDialogComponent],
  entryComponents: [ActionParamDeleteDialogComponent]
})
export class ZeemonActionParamModule {}
