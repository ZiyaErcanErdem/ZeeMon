import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { ActionScriptComponent } from './action-script.component';
import { ActionScriptDetailComponent } from './action-script-detail.component';
import { ActionScriptUpdateComponent } from './action-script-update.component';
import { ActionScriptDeleteDialogComponent } from './action-script-delete-dialog.component';
import { actionScriptRoute } from './action-script.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(actionScriptRoute)],
  declarations: [ActionScriptComponent, ActionScriptDetailComponent, ActionScriptUpdateComponent, ActionScriptDeleteDialogComponent],
  entryComponents: [ActionScriptDeleteDialogComponent]
})
export class ZeemonActionScriptModule {}
