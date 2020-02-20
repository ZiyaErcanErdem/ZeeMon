import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { ITaskExecution, TaskExecution } from 'app/shared/model/task-execution.model';
import { TaskExecutionService } from './task-execution.service';
import { ITask } from 'app/shared/model/task.model';
import { TaskService } from 'app/entities/task/task.service';
import { IFlowExecution } from 'app/shared/model/flow-execution.model';
import { FlowExecutionService } from 'app/entities/flow-execution/flow-execution.service';

type SelectableEntity = ITask | IFlowExecution;

@Component({
  selector: 'jhi-task-execution-update',
  templateUrl: './task-execution-update.component.html'
})
export class TaskExecutionUpdateComponent implements OnInit {
  isSaving = false;
  tasks: ITask[] = [];
  flowexecutions: IFlowExecution[] = [];

  editForm = this.fb.group({
    id: [],
    executionStartTime: [null, [Validators.required]],
    executionEndTime: [null, [Validators.required]],
    executionStatus: [null, [Validators.required]],
    task: [null, Validators.required],
    flowExecution: [null, Validators.required]
  });

  constructor(
    protected taskExecutionService: TaskExecutionService,
    protected taskService: TaskService,
    protected flowExecutionService: FlowExecutionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ taskExecution }) => {
      if (!taskExecution.id) {
        const today = moment().startOf('day');
        taskExecution.executionStartTime = today;
        taskExecution.executionEndTime = today;
      }

      this.updateForm(taskExecution);

      this.taskService.query().subscribe((res: HttpResponse<ITask[]>) => (this.tasks = res.body || []));

      this.flowExecutionService.query().subscribe((res: HttpResponse<IFlowExecution[]>) => (this.flowexecutions = res.body || []));
    });
  }

  updateForm(taskExecution: ITaskExecution): void {
    this.editForm.patchValue({
      id: taskExecution.id,
      executionStartTime: taskExecution.executionStartTime ? taskExecution.executionStartTime.format(DATE_TIME_FORMAT) : null,
      executionEndTime: taskExecution.executionEndTime ? taskExecution.executionEndTime.format(DATE_TIME_FORMAT) : null,
      executionStatus: taskExecution.executionStatus,
      task: taskExecution.task,
      flowExecution: taskExecution.flowExecution
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const taskExecution = this.createFromForm();
    if (taskExecution.id !== undefined) {
      this.subscribeToSaveResponse(this.taskExecutionService.update(taskExecution));
    } else {
      this.subscribeToSaveResponse(this.taskExecutionService.create(taskExecution));
    }
  }

  private createFromForm(): ITaskExecution {
    return {
      ...new TaskExecution(),
      id: this.editForm.get(['id'])!.value,
      executionStartTime: this.editForm.get(['executionStartTime'])!.value
        ? moment(this.editForm.get(['executionStartTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      executionEndTime: this.editForm.get(['executionEndTime'])!.value
        ? moment(this.editForm.get(['executionEndTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      executionStatus: this.editForm.get(['executionStatus'])!.value,
      task: this.editForm.get(['task'])!.value,
      flowExecution: this.editForm.get(['flowExecution'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<ITaskExecution>>): void {
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
