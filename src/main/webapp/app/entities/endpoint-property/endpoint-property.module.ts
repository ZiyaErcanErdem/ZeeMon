import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { EndpointPropertyComponent } from './endpoint-property.component';
import { EndpointPropertyDetailComponent } from './endpoint-property-detail.component';
import { EndpointPropertyUpdateComponent } from './endpoint-property-update.component';
import { EndpointPropertyDeleteDialogComponent } from './endpoint-property-delete-dialog.component';
import { endpointPropertyRoute } from './endpoint-property.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(endpointPropertyRoute)],
  declarations: [
    EndpointPropertyComponent,
    EndpointPropertyDetailComponent,
    EndpointPropertyUpdateComponent,
    EndpointPropertyDeleteDialogComponent
  ],
  entryComponents: [EndpointPropertyDeleteDialogComponent]
})
export class ZeemonEndpointPropertyModule {}
