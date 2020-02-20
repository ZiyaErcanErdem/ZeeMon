import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICheckScript } from 'app/shared/model/check-script.model';
import { CheckScriptService } from './check-script.service';

@Component({
  templateUrl: './check-script-delete-dialog.component.html'
})
export class CheckScriptDeleteDialogComponent {
  checkScript?: ICheckScript;

  constructor(
    protected checkScriptService: CheckScriptService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.checkScriptService.delete(id).subscribe(() => {
      this.eventManager.broadcast('checkScriptListModification');
      this.activeModal.close();
    });
  }
}
