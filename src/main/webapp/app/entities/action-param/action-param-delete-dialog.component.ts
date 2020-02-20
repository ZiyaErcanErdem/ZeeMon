import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActionParam } from 'app/shared/model/action-param.model';
import { ActionParamService } from './action-param.service';

@Component({
  templateUrl: './action-param-delete-dialog.component.html'
})
export class ActionParamDeleteDialogComponent {
  actionParam?: IActionParam;

  constructor(
    protected actionParamService: ActionParamService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.actionParamService.delete(id).subscribe(() => {
      this.eventManager.broadcast('actionParamListModification');
      this.activeModal.close();
    });
  }
}
