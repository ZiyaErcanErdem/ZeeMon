import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { EndpointComponent } from './endpoint.component';
import { EndpointDetailComponent } from './endpoint-detail.component';
import { EndpointUpdateComponent } from './endpoint-update.component';
import { EndpointDeleteDialogComponent } from './endpoint-delete-dialog.component';
import { endpointRoute } from './endpoint.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(endpointRoute)],
  declarations: [EndpointComponent, EndpointDetailComponent, EndpointUpdateComponent, EndpointDeleteDialogComponent],
  entryComponents: [EndpointDeleteDialogComponent]
})
export class ZeemonEndpointModule {}
