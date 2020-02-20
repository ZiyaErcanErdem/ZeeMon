import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAgent } from 'app/shared/model/agent.model';
import { AgentService } from './agent.service';

@Component({
  templateUrl: './agent-delete-dialog.component.html'
})
export class AgentDeleteDialogComponent {
  agent?: IAgent;

  constructor(protected agentService: AgentService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.agentService.delete(id).subscribe(() => {
      this.eventManager.broadcast('agentListModification');
      this.activeModal.close();
    });
  }
}
