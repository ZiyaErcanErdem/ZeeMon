import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IFieldMapping, FieldMapping } from 'app/shared/model/field-mapping.model';
import { FieldMappingService } from './field-mapping.service';
import { IContentMapper } from 'app/shared/model/content-mapper.model';
import { ContentMapperService } from 'app/entities/content-mapper/content-mapper.service';

@Component({
  selector: 'jhi-field-mapping-update',
  templateUrl: './field-mapping-update.component.html'
})
export class FieldMappingUpdateComponent implements OnInit {
  isSaving = false;
  contentmappers: IContentMapper[] = [];

  editForm = this.fb.group({
    id: [],
    sourceIndex: [null, [Validators.min(0)]],
    sourceName: [null, [Validators.maxLength(255)]],
    sourceFormat: [null, [Validators.minLength(0), Validators.maxLength(255)]],
    sourceStartIndex: [null, [Validators.min(0)]],
    sourceEndIndex: [null, [Validators.min(0)]],
    sourceDataType: [null, [Validators.required]],
    targetName: [null, [Validators.required, Validators.maxLength(255)]],
    targetColName: [null, [Validators.required, Validators.maxLength(255)]],
    targetDataType: [null, [Validators.required]],
    transformation: [null, [Validators.maxLength(2048)]],
    requiredData: [],
    contentMapper: [null, Validators.required]
  });

  constructor(
    protected fieldMappingService: FieldMappingService,
    protected contentMapperService: ContentMapperService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ fieldMapping }) => {
      this.updateForm(fieldMapping);

      this.contentMapperService.query().subscribe((res: HttpResponse<IContentMapper[]>) => (this.contentmappers = res.body || []));
    });
  }

  updateForm(fieldMapping: IFieldMapping): void {
    this.editForm.patchValue({
      id: fieldMapping.id,
      sourceIndex: fieldMapping.sourceIndex,
      sourceName: fieldMapping.sourceName,
      sourceFormat: fieldMapping.sourceFormat,
      sourceStartIndex: fieldMapping.sourceStartIndex,
      sourceEndIndex: fieldMapping.sourceEndIndex,
      sourceDataType: fieldMapping.sourceDataType,
      targetName: fieldMapping.targetName,
      targetColName: fieldMapping.targetColName,
      targetDataType: fieldMapping.targetDataType,
      transformation: fieldMapping.transformation,
      requiredData: fieldMapping.requiredData,
      contentMapper: fieldMapping.contentMapper
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const fieldMapping = this.createFromForm();
    if (fieldMapping.id !== undefined) {
      this.subscribeToSaveResponse(this.fieldMappingService.update(fieldMapping));
    } else {
      this.subscribeToSaveResponse(this.fieldMappingService.create(fieldMapping));
    }
  }

  private createFromForm(): IFieldMapping {
    return {
      ...new FieldMapping(),
      id: this.editForm.get(['id'])!.value,
      sourceIndex: this.editForm.get(['sourceIndex'])!.value,
      sourceName: this.editForm.get(['sourceName'])!.value,
      sourceFormat: this.editForm.get(['sourceFormat'])!.value,
      sourceStartIndex: this.editForm.get(['sourceStartIndex'])!.value,
      sourceEndIndex: this.editForm.get(['sourceEndIndex'])!.value,
      sourceDataType: this.editForm.get(['sourceDataType'])!.value,
      targetName: this.editForm.get(['targetName'])!.value,
      targetColName: this.editForm.get(['targetColName'])!.value,
      targetDataType: this.editForm.get(['targetDataType'])!.value,
      transformation: this.editForm.get(['transformation'])!.value,
      requiredData: this.editForm.get(['requiredData'])!.value,
      contentMapper: this.editForm.get(['contentMapper'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFieldMapping>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IContentMapper): any {
    return item.id;
  }
}
