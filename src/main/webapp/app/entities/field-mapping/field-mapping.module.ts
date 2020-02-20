import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { FieldMappingComponent } from './field-mapping.component';
import { FieldMappingDetailComponent } from './field-mapping-detail.component';
import { FieldMappingUpdateComponent } from './field-mapping-update.component';
import { FieldMappingDeleteDialogComponent } from './field-mapping-delete-dialog.component';
import { fieldMappingRoute } from './field-mapping.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(fieldMappingRoute)],
  declarations: [FieldMappingComponent, FieldMappingDetailComponent, FieldMappingUpdateComponent, FieldMappingDeleteDialogComponent],
  entryComponents: [FieldMappingDeleteDialogComponent]
})
export class ZeemonFieldMappingModule {}
