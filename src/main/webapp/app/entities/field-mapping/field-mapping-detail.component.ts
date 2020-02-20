import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFieldMapping } from 'app/shared/model/field-mapping.model';

@Component({
  selector: 'jhi-field-mapping-detail',
  templateUrl: './field-mapping-detail.component.html'
})
export class FieldMappingDetailComponent implements OnInit {
  fieldMapping: IFieldMapping | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fieldMapping }) => (this.fieldMapping = fieldMapping));
  }

  previousState(): void {
    window.history.back();
  }
}
