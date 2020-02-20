import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContentValidationError } from 'app/shared/model/content-validation-error.model';

@Component({
  selector: 'jhi-content-validation-error-detail',
  templateUrl: './content-validation-error-detail.component.html'
})
export class ContentValidationErrorDetailComponent implements OnInit {
  contentValidationError: IContentValidationError | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentValidationError }) => (this.contentValidationError = contentValidationError));
  }

  previousState(): void {
    window.history.back();
  }
}
