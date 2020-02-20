import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ZeemonSharedModule } from 'app/shared/shared.module';
import { EventTriggerComponent } from './event-trigger.component';
import { EventTriggerDetailComponent } from './event-trigger-detail.component';
import { EventTriggerUpdateComponent } from './event-trigger-update.component';
import { EventTriggerDeleteDialogComponent } from './event-trigger-delete-dialog.component';
import { eventTriggerRoute } from './event-trigger.route';

@NgModule({
  imports: [ZeemonSharedModule, RouterModule.forChild(eventTriggerRoute)],
  declarations: [EventTriggerComponent, EventTriggerDetailComponent, EventTriggerUpdateComponent, EventTriggerDeleteDialogComponent],
  entryComponents: [EventTriggerDeleteDialogComponent]
})
export class ZeemonEventTriggerModule {}
