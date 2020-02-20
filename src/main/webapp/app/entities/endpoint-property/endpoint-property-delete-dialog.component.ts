import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IEndpointProperty } from 'app/shared/model/endpoint-property.model';
import { EndpointPropertyService } from './endpoint-property.service';

@Component({
  templateUrl: './endpoint-property-delete-dialog.component.html'
})
export class EndpointPropertyDeleteDialogComponent {
  endpointProperty?: IEndpointProperty;

  constructor(
    protected endpointPropertyService: EndpointPropertyService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.endpointPropertyService.delete(id).subscribe(() => {
      this.eventManager.broadcast('endpointPropertyListModification');
      this.activeModal.close();
    });
  }
}
