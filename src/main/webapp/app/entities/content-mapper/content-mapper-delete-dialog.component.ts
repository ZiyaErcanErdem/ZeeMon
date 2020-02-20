import { Component } from '@angular/core';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IContentMapper } from 'app/shared/model/content-mapper.model';
import { ContentMapperService } from './content-mapper.service';

@Component({
  templateUrl: './content-mapper-delete-dialog.component.html'
})
export class ContentMapperDeleteDialogComponent {
  contentMapper?: IContentMapper;

  constructor(
    protected contentMapperService: ContentMapperService,
    public activeModal: NgbActiveModal,
    protected eventManager: JhiEventManager
  ) {}

  cancel(): void {
    this.activeModal.dismiss();
  }

  confirmDelete(id: number): void {
    this.contentMapperService.delete(id).subscribe(() => {
      this.eventManager.broadcast('contentMapperListModification');
      this.activeModal.close();
    });
  }
}
