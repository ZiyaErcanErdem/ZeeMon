import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAction } from 'app/shared/model/action.model';
import { ActionService } from './action.service';

@Component({
  templateUrl: './action-delete-dialog.component.html'
})
export class ActionDeleteDialogComponent {
  action?: IAction;

  constructor(protected actionService: ActionService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.actionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('actionListModification');
      this.activeModal.close();
    });
  }
}
