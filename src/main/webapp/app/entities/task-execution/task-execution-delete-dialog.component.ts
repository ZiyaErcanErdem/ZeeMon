import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITaskExecution } from 'app/shared/model/task-execution.model';
import { TaskExecutionService } from './task-execution.service';

@Component({
  templateUrl: './task-execution-delete-dialog.component.html'
})
export class TaskExecutionDeleteDialogComponent {
  taskExecution?: ITaskExecution;

  constructor(
    protected taskExecutionService: TaskExecutionService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.taskExecutionService.delete(id).subscribe(() => {
      this.eventManager.broadcast('taskExecutionListModification');
      this.activeModal.close();
    });
  }
}
