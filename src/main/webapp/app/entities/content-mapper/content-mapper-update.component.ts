import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContentMapper, ContentMapper } from 'app/shared/model/content-mapper.model';
import { ContentMapperService } from './content-mapper.service';

@Component({
  selector: 'jhi-content-mapper-update',
  templateUrl: './content-mapper-update.component.html'
})
export class ContentMapperUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    mapperName: [null, [Validators.required, Validators.maxLength(255)]],
    itemFormat: [null, [Validators.required]],
    containsHeader: [],
    fieldDelimiter: [null, [Validators.required, Validators.maxLength(50)]]
  });

  constructor(protected contentMapperService: ContentMapperService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentMapper }) => {
      this.updateForm(contentMapper);
    });
  }

  updateForm(contentMapper: IContentMapper): void {
    this.editForm.patchValue({
      id: contentMapper.id,
      mapperName: contentMapper.mapperName,
      itemFormat: contentMapper.itemFormat,
      containsHeader: contentMapper.containsHeader,
      fieldDelimiter: contentMapper.fieldDelimiter
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contentMapper = this.createFromForm();
    if (contentMapper.id !== undefined) {
      this.subscribeToSaveResponse(this.contentMapperService.update(contentMapper));
    } else {
      this.subscribeToSaveResponse(this.contentMapperService.create(contentMapper));
    }
  }

  private createFromForm(): IContentMapper {
    return {
      ...new ContentMapper(),
      id: this.editForm.get(['id'])!.value,
      mapperName: this.editForm.get(['mapperName'])!.value,
      itemFormat: this.editForm.get(['itemFormat'])!.value,
      containsHeader: this.editForm.get(['containsHeader'])!.value,
      fieldDelimiter: this.editForm.get(['fieldDelimiter'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContentMapper>>): void {
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
}
