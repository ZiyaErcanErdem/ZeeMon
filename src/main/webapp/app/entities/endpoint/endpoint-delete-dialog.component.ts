import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEndpoint } from 'app/shared/model/endpoint.model';
import { EndpointService } from './endpoint.service';

@Component({
  templateUrl: './endpoint-delete-dialog.component.html'
})
export class EndpointDeleteDialogComponent {
  endpoint?: IEndpoint;

  constructor(protected endpointService: EndpointService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.endpointService.delete(id).subscribe(() => {
      this.eventManager.broadcast('endpointListModification');
      this.activeModal.close();
    });
  }
}
