import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IActionExecution, ActionExecution } from 'app/shared/model/action-execution.model';
import { ActionExecutionService } from './action-execution.service';
import { IAction } from 'app/shared/model/action.model';
import { ActionService } from 'app/entities/action/action.service';

@Component({
  selector: 'jhi-action-execution-update',
  templateUrl: './action-execution-update.component.html'
})
export class ActionExecutionUpdateComponent implements OnInit {
  isSaving = false;
  actions: IAction[] = [];

  editForm = this.fb.group({
    id: [],
    executionStartTime: [null, [Validators.required]],
    executionEndTime: [null, [Validators.required]],
    executionStatus: [null, [Validators.required]],
    actionLog: [null, [Validators.maxLength(2048)]],
    action: [null, Validators.required]
  });

  constructor(
    protected actionExecutionService: ActionExecutionService,
    protected actionService: ActionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actionExecution }) => {
      if (!actionExecution.id) {
        const today = moment().startOf('day');
        actionExecution.executionStartTime = today;
        actionExecution.executionEndTime = today;
      }

      this.updateForm(actionExecution);

      this.actionService.query().subscribe((res: HttpResponse<IAction[]>) => (this.actions = res.body || []));
    });
  }

  updateForm(actionExecution: IActionExecution): void {
    this.editForm.patchValue({
      id: actionExecution.id,
      executionStartTime: actionExecution.executionStartTime ? actionExecution.executionStartTime.format(DATE_TIME_FORMAT) : null,
      executionEndTime: actionExecution.executionEndTime ? actionExecution.executionEndTime.format(DATE_TIME_FORMAT) : null,
      executionStatus: actionExecution.executionStatus,
      actionLog: actionExecution.actionLog,
      action: actionExecution.action
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const actionExecution = this.createFromForm();
    if (actionExecution.id !== undefined) {
      this.subscribeToSaveResponse(this.actionExecutionService.update(actionExecution));
    } else {
      this.subscribeToSaveResponse(this.actionExecutionService.create(actionExecution));
    }
  }

  private createFromForm(): IActionExecution {
    return {
      ...new ActionExecution(),
      id: this.editForm.get(['id'])!.value,
      executionStartTime: this.editForm.get(['executionStartTime'])!.value
        ? moment(this.editForm.get(['executionStartTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      executionEndTime: this.editForm.get(['executionEndTime'])!.value
        ? moment(this.editForm.get(['executionEndTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      executionStatus: this.editForm.get(['executionStatus'])!.value,
      actionLog: this.editForm.get(['actionLog'])!.value,
      action: this.editForm.get(['action'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IActionExecution>>): void {
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

  trackById(index: number, item: IAction): any {
    return item.id;
  }
}
