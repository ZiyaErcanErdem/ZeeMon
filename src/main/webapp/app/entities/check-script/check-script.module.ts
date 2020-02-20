import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { CheckScriptComponent } from './check-script.component';
import { CheckScriptDetailComponent } from './check-script-detail.component';
import { CheckScriptUpdateComponent } from './check-script-update.component';
import { CheckScriptDeleteDialogComponent } from './check-script-delete-dialog.component';
import { checkScriptRoute } from './check-script.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(checkScriptRoute)],
  declarations: [CheckScriptComponent, CheckScriptDetailComponent, CheckScriptUpdateComponent, CheckScriptDeleteDialogComponent],
  entryComponents: [CheckScriptDeleteDialogComponent]
})
export class ZeemonCheckScriptModule {}
