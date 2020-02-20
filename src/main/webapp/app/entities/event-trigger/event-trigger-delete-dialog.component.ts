import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEventTrigger } from 'app/shared/model/event-trigger.model';
import { EventTriggerService } from './event-trigger.service';

@Component({
  templateUrl: './event-trigger-delete-dialog.component.html'
})
export class EventTriggerDeleteDialogComponent {
  eventTrigger?: IEventTrigger;

  constructor(
    protected eventTriggerService: EventTriggerService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.eventTriggerService.delete(id).subscribe(() => {
      this.eventManager.broadcast('eventTriggerListModification');
      this.activeModal.close();
    });
  }
}
