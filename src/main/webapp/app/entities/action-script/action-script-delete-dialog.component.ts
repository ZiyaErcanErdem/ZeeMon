import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IActionScript } from 'app/shared/model/action-script.model';
import { ActionScriptService } from './action-script.service';

@Component({
  templateUrl: './action-script-delete-dialog.component.html'
})
export class ActionScriptDeleteDialogComponent {
  actionScript?: IActionScript;

  constructor(
    protected actionScriptService: ActionScriptService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.actionScriptService.delete(id).subscribe(() => {
      this.eventManager.broadcast('actionScriptListModification');
      this.activeModal.close();
    });
  }
}
