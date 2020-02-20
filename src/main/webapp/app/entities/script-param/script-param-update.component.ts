import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IScriptParam, ScriptParam } from 'app/shared/model/script-param.model';
import { ScriptParamService } from './script-param.service';
import { ICheckScript } from 'app/shared/model/check-script.model';
import { CheckScriptService } from 'app/entities/check-script/check-script.service';

@Component({
  selector: 'jhi-script-param-update',
  templateUrl: './script-param-update.component.html'
})
export class ScriptParamUpdateComponent implements OnInit {
  isSaving = false;
  checkscripts: ICheckScript[] = [];

  editForm = this.fb.group({
    id: [],
    paramName: [null, [Validators.required, Validators.maxLength(255)]],
    paramDataType: [null, [Validators.required]],
    paramValue: [null, [Validators.maxLength(255)]],
    paramExpression: [null, [Validators.maxLength(255)]],
    checkScript: [null, Validators.required]
  });

  constructor(
    protected scriptParamService: ScriptParamService,
    protected checkScriptService: CheckScriptService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scriptParam }) => {
      this.updateForm(scriptParam);

      this.checkScriptService.query().subscribe((res: HttpResponse<ICheckScript[]>) => (this.checkscripts = res.body || []));
    });
  }

  updateForm(scriptParam: IScriptParam): void {
    this.editForm.patchValue({
      id: scriptParam.id,
      paramName: scriptParam.paramName,
      paramDataType: scriptParam.paramDataType,
      paramValue: scriptParam.paramValue,
      paramExpression: scriptParam.paramExpression,
      checkScript: scriptParam.checkScript
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const scriptParam = this.createFromForm();
    if (scriptParam.id !== undefined) {
      this.subscribeToSaveResponse(this.scriptParamService.update(scriptParam));
    } else {
      this.subscribeToSaveResponse(this.scriptParamService.create(scriptParam));
    }
  }

  private createFromForm(): IScriptParam {
    return {
      ...new ScriptParam(),
      id: this.editForm.get(['id'])!.value,
      paramName: this.editForm.get(['paramName'])!.value,
      paramDataType: this.editForm.get(['paramDataType'])!.value,
      paramValue: this.editForm.get(['paramValue'])!.value,
      paramExpression: this.editForm.get(['paramExpression'])!.value,
      checkScript: this.editForm.get(['checkScript'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IScriptParam>>): void {
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

  trackById(index: number, item: ICheckScript): any {
    return item.id;
  }
}
