import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { ContentValidationErrorComponent } from './content-validation-error.component';
import { ContentValidationErrorDetailComponent } from './content-validation-error-detail.component';
import { ContentValidationErrorUpdateComponent } from './content-validation-error-update.component';
import { ContentValidationErrorDeleteDialogComponent } from './content-validation-error-delete-dialog.component';
import { contentValidationErrorRoute } from './content-validation-error.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(contentValidationErrorRoute)],
  declarations: [
    ContentValidationErrorComponent,
    ContentValidationErrorDetailComponent,
    ContentValidationErrorUpdateComponent,
    ContentValidationErrorDeleteDialogComponent
  ],
  entryComponents: [ContentValidationErrorDeleteDialogComponent]
})
export class ZeemonContentValidationErrorModule {}
