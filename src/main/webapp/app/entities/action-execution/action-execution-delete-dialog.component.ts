import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActionExecution } from 'app/shared/model/action-execution.model';
import { ActionExecutionService } from './action-execution.service';

@Component({
  templateUrl: './action-execution-delete-dialog.component.html'
})
export class ActionExecutionDeleteDialogComponent {
  actionExecution?: IActionExecution;

  constructor(
    protected actionExecutionService: ActionExecutionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.actionExecutionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('actionExecutionListModification');
      this.activeModal.close();
    });
  }
}
