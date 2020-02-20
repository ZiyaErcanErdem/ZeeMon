import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IActionScript, ActionScript } from 'app/shared/model/action-script.model';
import { ActionScriptService } from './action-script.service';
import { IEndpoint } from 'app/shared/model/endpoint.model';
import { EndpointService } from 'app/entities/endpoint/endpoint.service';

@Component({
  selector: 'jhi-action-script-update',
  templateUrl: './action-script-update.component.html'
})
export class ActionScriptUpdateComponent implements OnInit {
  isSaving = false;
  endpoints: IEndpoint[] = [];

  editForm = this.fb.group({
    id: [],
    actionCode: [null, [Validators.required, Validators.maxLength(20)]],
    scriptName: [null, [Validators.required, Validators.maxLength(255)]],
    scriptType: [null, [Validators.required]],
    actionType: [null, [Validators.required]],
    actionSource: [null, [Validators.maxLength(2048)]],
    endpoint: [null, Validators.required]
  });

  constructor(
    protected actionScriptService: ActionScriptService,
    protected endpointService: EndpointService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actionScript }) => {
      this.updateForm(actionScript);

      this.endpointService.query().subscribe((res: HttpResponse<IEndpoint[]>) => (this.endpoints = res.body || []));
    });
  }

  updateForm(actionScript: IActionScript): void {
    this.editForm.patchValue({
      id: actionScript.id,
      actionCode: actionScript.actionCode,
      scriptName: actionScript.scriptName,
      scriptType: actionScript.scriptType,
      actionType: actionScript.actionType,
      actionSource: actionScript.actionSource,
      endpoint: actionScript.endpoint
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const actionScript = this.createFromForm();
    if (actionScript.id !== undefined) {
      this.subscribeToSaveResponse(this.actionScriptService.update(actionScript));
    } else {
      this.subscribeToSaveResponse(this.actionScriptService.create(actionScript));
    }
  }

  private createFromForm(): IActionScript {
    return {
      ...new ActionScript(),
      id: this.editForm.get(['id'])!.value,
      actionCode: this.editForm.get(['actionCode'])!.value,
      scriptName: this.editForm.get(['scriptName'])!.value,
      scriptType: this.editForm.get(['scriptType'])!.value,
      actionType: this.editForm.get(['actionType'])!.value,
      actionSource: this.editForm.get(['actionSource'])!.value,
      endpoint: this.editForm.get(['endpoint'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActionScript>>): void {
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

  trackById(index: number, item: IEndpoint): any {
    return item.id;
  }
}
