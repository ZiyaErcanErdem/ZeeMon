import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFlowExecution } from 'app/shared/model/flow-execution.model';
import { FlowExecutionService } from './flow-execution.service';

@Component({
  templateUrl: './flow-execution-delete-dialog.component.html'
})
export class FlowExecutionDeleteDialogComponent {
  flowExecution?: IFlowExecution;

  constructor(
    protected flowExecutionService: FlowExecutionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.flowExecutionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('flowExecutionListModification');
      this.activeModal.close();
    });
  }
}
