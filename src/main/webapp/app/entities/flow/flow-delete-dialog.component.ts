import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFlow } from 'app/shared/model/flow.model';
import { FlowService } from './flow.service';

@Component({
  templateUrl: './flow-delete-dialog.component.html'
})
export class FlowDeleteDialogComponent {
  flow?: IFlow;

  constructor(protected flowService: FlowService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.flowService.delete(id).subscribe(() => {
      this.eventManager.broadcast('flowListModification');
      this.activeModal.close();
    });
  }
}
