import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFlowExecution, FlowExecution } from 'app/shared/model/flow-execution.model';
import { FlowExecutionService } from './flow-execution.service';
import { IFlow } from 'app/shared/model/flow.model';
import { FlowService } from 'app/entities/flow/flow.service';

@Component({
  selector: 'jhi-flow-execution-update',
  templateUrl: './flow-execution-update.component.html'
})
export class FlowExecutionUpdateComponent implements OnInit {
  isSaving = false;
  flows: IFlow[] = [];

  editForm = this.fb.group({
    id: [],
    executionStartTime: [null, [Validators.required]],
    executionEndTime: [null, [Validators.required]],
    totalTaskCount: [null, [Validators.min(0)]],
    runningTaskCount: [null, [Validators.min(0)]],
    errorTaskCount: [null, [Validators.min(0)]],
    executionStatus: [null, [Validators.required]],
    flow: [null, Validators.required]
  });

  constructor(
    protected flowExecutionService: FlowExecutionService,
    protected flowService: FlowService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ flowExecution }) => {
      if (!flowExecution.id) {
        const today = moment().startOf('day');
        flowExecution.executionStartTime = today;
        flowExecution.executionEndTime = today;
      }

      this.updateForm(flowExecution);

      this.flowService.query().subscribe((res: HttpResponse<IFlow[]>) => (this.flows = res.body || []));
    });
  }

  updateForm(flowExecution: IFlowExecution): void {
    this.editForm.patchValue({
      id: flowExecution.id,
      executionStartTime: flowExecution.executionStartTime ? flowExecution.executionStartTime.format(DATE_TIME_FORMAT) : null,
      executionEndTime: flowExecution.executionEndTime ? flowExecution.executionEndTime.format(DATE_TIME_FORMAT) : null,
      totalTaskCount: flowExecution.totalTaskCount,
      runningTaskCount: flowExecution.runningTaskCount,
      errorTaskCount: flowExecution.errorTaskCount,
      executionStatus: flowExecution.executionStatus,
      flow: flowExecution.flow
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const flowExecution = this.createFromForm();
    if (flowExecution.id !== undefined) {
      this.subscribeToSaveResponse(this.flowExecutionService.update(flowExecution));
    } else {
      this.subscribeToSaveResponse(this.flowExecutionService.create(flowExecution));
    }
  }

  private createFromForm(): IFlowExecution {
    return {
      ...new FlowExecution(),
      id: this.editForm.get(['id'])!.value,
      executionStartTime: this.editForm.get(['executionStartTime'])!.value
        ? moment(this.editForm.get(['executionStartTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      executionEndTime: this.editForm.get(['executionEndTime'])!.value
        ? moment(this.editForm.get(['executionEndTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      totalTaskCount: this.editForm.get(['totalTaskCount'])!.value,
      runningTaskCount: this.editForm.get(['runningTaskCount'])!.value,
      errorTaskCount: this.editForm.get(['errorTaskCount'])!.value,
      executionStatus: this.editForm.get(['executionStatus'])!.value,
      flow: this.editForm.get(['flow'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFlowExecution>>): void {
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

  trackById(index: number, item: IFlow): any {
    return item.id;
  }
}
