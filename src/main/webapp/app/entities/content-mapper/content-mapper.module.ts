import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { ContentMapperComponent } from './content-mapper.component';
import { ContentMapperDetailComponent } from './content-mapper-detail.component';
import { ContentMapperUpdateComponent } from './content-mapper-update.component';
import { ContentMapperDeleteDialogComponent } from './content-mapper-delete-dialog.component';
import { contentMapperRoute } from './content-mapper.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(contentMapperRoute)],
  declarations: [ContentMapperComponent, ContentMapperDetailComponent, ContentMapperUpdateComponent, ContentMapperDeleteDialogComponent],
  entryComponents: [ContentMapperDeleteDialogComponent]
})
export class ZeemonContentMapperModule {}
