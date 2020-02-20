import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IActionParam, ActionParam } from 'app/shared/model/action-param.model';
import { ActionParamService } from './action-param.service';
import { IActionScript } from 'app/shared/model/action-script.model';
import { ActionScriptService } from 'app/entities/action-script/action-script.service';

@Component({
  selector: 'jhi-action-param-update',
  templateUrl: './action-param-update.component.html'
})
export class ActionParamUpdateComponent implements OnInit {
  isSaving = false;
  actionscripts: IActionScript[] = [];

  editForm = this.fb.group({
    id: [],
    paramName: [null, [Validators.required, Validators.maxLength(255)]],
    paramDataType: [null, [Validators.required]],
    paramValue: [null, [Validators.maxLength(255)]],
    paramExpression: [null, [Validators.maxLength(255)]],
    actionScript: [null, Validators.required]
  });

  constructor(
    protected actionParamService: ActionParamService,
    protected actionScriptService: ActionScriptService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actionParam }) => {
      this.updateForm(actionParam);

      this.actionScriptService.query().subscribe((res: HttpResponse<IActionScript[]>) => (this.actionscripts = res.body || []));
    });
  }

  updateForm(actionParam: IActionParam): void {
    this.editForm.patchValue({
      id: actionParam.id,
      paramName: actionParam.paramName,
      paramDataType: actionParam.paramDataType,
      paramValue: actionParam.paramValue,
      paramExpression: actionParam.paramExpression,
      actionScript: actionParam.actionScript
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const actionParam = this.createFromForm();
    if (actionParam.id !== undefined) {
      this.subscribeToSaveResponse(this.actionParamService.update(actionParam));
    } else {
      this.subscribeToSaveResponse(this.actionParamService.create(actionParam));
    }
  }

  private createFromForm(): IActionParam {
    return {
      ...new ActionParam(),
      id: this.editForm.get(['id'])!.value,
      paramName: this.editForm.get(['paramName'])!.value,
      paramDataType: this.editForm.get(['paramDataType'])!.value,
      paramValue: this.editForm.get(['paramValue'])!.value,
      paramExpression: this.editForm.get(['paramExpression'])!.value,
      actionScript: this.editForm.get(['actionScript'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActionParam>>): void {
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

  trackById(index: number, item: IActionScript): any {
    return item.id;
  }
}
