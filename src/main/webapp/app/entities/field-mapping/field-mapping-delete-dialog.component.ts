import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IFieldMapping } from 'app/shared/model/field-mapping.model';
import { FieldMappingService } from './field-mapping.service';

@Component({
  templateUrl: './field-mapping-delete-dialog.component.html'
})
export class FieldMappingDeleteDialogComponent {
  fieldMapping?: IFieldMapping;

  constructor(
    protected fieldMappingService: FieldMappingService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.fieldMappingService.delete(id).subscribe(() => {
      this.eventManager.broadcast('fieldMappingListModification');
      this.activeModal.close();
    });
  }
}
