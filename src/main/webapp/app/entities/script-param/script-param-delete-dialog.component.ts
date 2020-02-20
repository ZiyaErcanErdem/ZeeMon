import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IScriptParam } from 'app/shared/model/script-param.model';
import { ScriptParamService } from './script-param.service';

@Component({
  templateUrl: './script-param-delete-dialog.component.html'
})
export class ScriptParamDeleteDialogComponent {
  scriptParam?: IScriptParam;

  constructor(
    protected scriptParamService: ScriptParamService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.scriptParamService.delete(id).subscribe(() => {
      this.eventManager.broadcast('scriptParamListModification');
      this.activeModal.close();
    });
  }
}
