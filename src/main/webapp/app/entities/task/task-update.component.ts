import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITask, Task } from 'app/shared/model/task.model';
import { TaskService } from './task.service';
import { IAgent } from 'app/shared/model/agent.model';
import { AgentService } from 'app/entities/agent/agent.service';
import { ICheckScript } from 'app/shared/model/check-script.model';
import { CheckScriptService } from 'app/entities/check-script/check-script.service';
import { IFlow } from 'app/shared/model/flow.model';
import { FlowService } from 'app/entities/flow/flow.service';

type SelectableEntity = IAgent | ICheckScript | IFlow;

@Component({
  selector: 'jhi-task-update',
  templateUrl: './task-update.component.html'
})
export class TaskUpdateComponent implements OnInit {
  isSaving = false;
  agents: IAgent[] = [];
  checkscripts: ICheckScript[] = [];
  flows: IFlow[] = [];

  editForm = this.fb.group({
    id: [],
    taskName: [null, [Validators.required, Validators.maxLength(255)]],
    taskDescription: [null, [Validators.maxLength(512)]],
    nextExecutionStartTime: [],
    taskState: [null, [Validators.required]],
    agent: [null, Validators.required],
    checkScript: [null, Validators.required],
    flow: []
  });

  constructor(
    protected taskService: TaskService,
    protected agentService: AgentService,
    protected checkScriptService: CheckScriptService,
    protected flowService: FlowService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ task }) => {
      if (!task.id) {
        const today = moment().startOf('day');
        task.nextExecutionStartTime = today;
      }

      this.updateForm(task);

      this.agentService.query().subscribe((res: HttpResponse<IAgent[]>) => (this.agents = res.body || []));

      this.checkScriptService.query().subscribe((res: HttpResponse<ICheckScript[]>) => (this.checkscripts = res.body || []));

      this.flowService.query().subscribe((res: HttpResponse<IFlow[]>) => (this.flows = res.body || []));
    });
  }

  updateForm(task: ITask): void {
    this.editForm.patchValue({
      id: task.id,
      taskName: task.taskName,
      taskDescription: task.taskDescription,
      nextExecutionStartTime: task.nextExecutionStartTime ? task.nextExecutionStartTime.format(DATE_TIME_FORMAT) : null,
      taskState: task.taskState,
      agent: task.agent,
      checkScript: task.checkScript,
      flow: task.flow
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const task = this.createFromForm();
    if (task.id !== undefined) {
      this.subscribeToSaveResponse(this.taskService.update(task));
    } else {
      this.subscribeToSaveResponse(this.taskService.create(task));
    }
  }

  private createFromForm(): ITask {
    return {
      ...new Task(),
      id: this.editForm.get(['id'])!.value,
      taskName: this.editForm.get(['taskName'])!.value,
      taskDescription: this.editForm.get(['taskDescription'])!.value,
      nextExecutionStartTime: this.editForm.get(['nextExecutionStartTime'])!.value
        ? moment(this.editForm.get(['nextExecutionStartTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      taskState: this.editForm.get(['taskState'])!.value,
      agent: this.editForm.get(['agent'])!.value,
      checkScript: this.editForm.get(['checkScript'])!.value,
      flow: this.editForm.get(['flow'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITask>>): void {
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
