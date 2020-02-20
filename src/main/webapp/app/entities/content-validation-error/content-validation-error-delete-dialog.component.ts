import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContentValidationError } from 'app/shared/model/content-validation-error.model';
import { ContentValidationErrorService } from './content-validation-error.service';

@Component({
  templateUrl: './content-validation-error-delete-dialog.component.html'
})
export class ContentValidationErrorDeleteDialogComponent {
  contentValidationError?: IContentValidationError;

  constructor(
    protected contentValidationErrorService: ContentValidationErrorService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contentValidationErrorService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contentValidationErrorListModification');
      this.activeModal.close();
    });
  }
}
