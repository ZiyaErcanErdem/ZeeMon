import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { ICheckScript, CheckScript } from 'app/shared/model/check-script.model';
import { CheckScriptService } from './check-script.service';
import { IEndpoint } from 'app/shared/model/endpoint.model';
import { EndpointService } from 'app/entities/endpoint/endpoint.service';
import { IContentMapper } from 'app/shared/model/content-mapper.model';
import { ContentMapperService } from 'app/entities/content-mapper/content-mapper.service';

type SelectableEntity = IEndpoint | IContentMapper;

@Component({
  selector: 'jhi-check-script-update',
  templateUrl: './check-script-update.component.html'
})
export class CheckScriptUpdateComponent implements OnInit {
  isSaving = false;
  endpoints: IEndpoint[] = [];
  contentmappers: IContentMapper[] = [];

  editForm = this.fb.group({
    id: [],
    scriptName: [null, [Validators.required, Validators.maxLength(255)]],
    scriptType: [null, [Validators.required]],
    scriptSource: [null, [Validators.maxLength(2048)]],
    actionRuleExpression: [null, [Validators.maxLength(2048)]],
    endpoint: [null, Validators.required],
    contentMapper: [null, Validators.required]
  });

  constructor(
    protected checkScriptService: CheckScriptService,
    protected endpointService: EndpointService,
    protected contentMapperService: ContentMapperService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkScript }) => {
      this.updateForm(checkScript);

      this.endpointService.query().subscribe((res: HttpResponse<IEndpoint[]>) => (this.endpoints = res.body || []));

      this.contentMapperService.query().subscribe((res: HttpResponse<IContentMapper[]>) => (this.contentmappers = res.body || []));
    });
  }

  updateForm(checkScript: ICheckScript): void {
    this.editForm.patchValue({
      id: checkScript.id,
      scriptName: checkScript.scriptName,
      scriptType: checkScript.scriptType,
      scriptSource: checkScript.scriptSource,
      actionRuleExpression: checkScript.actionRuleExpression,
      endpoint: checkScript.endpoint,
      contentMapper: checkScript.contentMapper
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const checkScript = this.createFromForm();
    if (checkScript.id !== undefined) {
      this.subscribeToSaveResponse(this.checkScriptService.update(checkScript));
    } else {
      this.subscribeToSaveResponse(this.checkScriptService.create(checkScript));
    }
  }

  private createFromForm(): ICheckScript {
    return {
      ...new CheckScript(),
      id: this.editForm.get(['id'])!.value,
      scriptName: this.editForm.get(['scriptName'])!.value,
      scriptType: this.editForm.get(['scriptType'])!.value,
      scriptSource: this.editForm.get(['scriptSource'])!.value,
      actionRuleExpression: this.editForm.get(['actionRuleExpression'])!.value,
      endpoint: this.editForm.get(['endpoint'])!.value,
      contentMapper: this.editForm.get(['contentMapper'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ICheckScript>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
