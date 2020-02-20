import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IAction, Action } from 'app/shared/model/action.model';
import { ActionService } from './action.service';
import { IAgent } from 'app/shared/model/agent.model';
import { AgentService } from 'app/entities/agent/agent.service';
import { IActionScript } from 'app/shared/model/action-script.model';
import { ActionScriptService } from 'app/entities/action-script/action-script.service';

type SelectableEntity = IAgent | IActionScript;

@Component({
  selector: 'jhi-action-update',
  templateUrl: './action-update.component.html'
})
export class ActionUpdateComponent implements OnInit {
  isSaving = false;
  agents: IAgent[] = [];
  actionscripts: IActionScript[] = [];

  editForm = this.fb.group({
    id: [],
    actionName: [null, [Validators.required, Validators.maxLength(255)]],
    actionDescription: [null, [Validators.maxLength(512)]],
    nextExecutionStartTime: [],
    actionState: [],
    resolutionRuleExpression: [null, [Validators.maxLength(2048)]],
    agent: [null, Validators.required],
    actionScript: [null, Validators.required]
  });

  constructor(
    protected actionService: ActionService,
    protected agentService: AgentService,
    protected actionScriptService: ActionScriptService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ action }) => {
      if (!action.id) {
        const today = moment().startOf('day');
        action.nextExecutionStartTime = today;
      }

      this.updateForm(action);

      this.agentService.query().subscribe((res: HttpResponse<IAgent[]>) => (this.agents = res.body || []));

      this.actionScriptService.query().subscribe((res: HttpResponse<IActionScript[]>) => (this.actionscripts = res.body || []));
    });
  }

  updateForm(action: IAction): void {
    this.editForm.patchValue({
      id: action.id,
      actionName: action.actionName,
      actionDescription: action.actionDescription,
      nextExecutionStartTime: action.nextExecutionStartTime ? action.nextExecutionStartTime.format(DATE_TIME_FORMAT) : null,
      actionState: action.actionState,
      resolutionRuleExpression: action.resolutionRuleExpression,
      agent: action.agent,
      actionScript: action.actionScript
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const action = this.createFromForm();
    if (action.id !== undefined) {
      this.subscribeToSaveResponse(this.actionService.update(action));
    } else {
      this.subscribeToSaveResponse(this.actionService.create(action));
    }
  }

  private createFromForm(): IAction {
    return {
      ...new Action(),
      id: this.editForm.get(['id'])!.value,
      actionName: this.editForm.get(['actionName'])!.value,
      actionDescription: this.editForm.get(['actionDescription'])!.value,
      nextExecutionStartTime: this.editForm.get(['nextExecutionStartTime'])!.value
        ? moment(this.editForm.get(['nextExecutionStartTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      actionState: this.editForm.get(['actionState'])!.value,
      resolutionRuleExpression: this.editForm.get(['resolutionRuleExpression'])!.value,
      agent: this.editForm.get(['agent'])!.value,
      actionScript: this.editForm.get(['actionScript'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAction>>): void {
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
