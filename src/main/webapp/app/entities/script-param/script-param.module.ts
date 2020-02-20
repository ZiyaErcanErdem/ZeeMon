import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { ScriptParamComponent } from './script-param.component';
import { ScriptParamDetailComponent } from './script-param-detail.component';
import { ScriptParamUpdateComponent } from './script-param-update.component';
import { ScriptParamDeleteDialogComponent } from './script-param-delete-dialog.component';
import { scriptParamRoute } from './script-param.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(scriptParamRoute)],
  declarations: [ScriptParamComponent, ScriptParamDetailComponent, ScriptParamUpdateComponent, ScriptParamDeleteDialogComponent],
  entryComponents: [ScriptParamDeleteDialogComponent]
})
export class ZeemonScriptParamModule {}
