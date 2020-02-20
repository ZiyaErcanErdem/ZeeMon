import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IFlow, Flow } from 'app/shared/model/flow.model';
import { FlowService } from './flow.service';
import { IEventTrigger } from 'app/shared/model/event-trigger.model';
import { EventTriggerService } from 'app/entities/event-trigger/event-trigger.service';

@Component({
  selector: 'jhi-flow-update',
  templateUrl: './flow-update.component.html'
})
export class FlowUpdateComponent implements OnInit {
  isSaving = false;
  eventtriggers: IEventTrigger[] = [];

  editForm = this.fb.group({
    id: [],
    flowName: [null, [Validators.required, Validators.maxLength(255)]],
    flowDescription: [null, [Validators.maxLength(512)]],
    nextExecutionStartTime: [],
    flowState: [null, [Validators.required]],
    eventTrigger: []
  });

  constructor(
    protected flowService: FlowService,
    protected eventTriggerService: EventTriggerService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ flow }) => {
      if (!flow.id) {
        const today = moment().startOf('day');
        flow.nextExecutionStartTime = today;
      }

      this.updateForm(flow);

      this.eventTriggerService.query().subscribe((res: HttpResponse<IEventTrigger[]>) => (this.eventtriggers = res.body || []));
    });
  }

  updateForm(flow: IFlow): void {
    this.editForm.patchValue({
      id: flow.id,
      flowName: flow.flowName,
      flowDescription: flow.flowDescription,
      nextExecutionStartTime: flow.nextExecutionStartTime ? flow.nextExecutionStartTime.format(DATE_TIME_FORMAT) : null,
      flowState: flow.flowState,
      eventTrigger: flow.eventTrigger
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const flow = this.createFromForm();
    if (flow.id !== undefined) {
      this.subscribeToSaveResponse(this.flowService.update(flow));
    } else {
      this.subscribeToSaveResponse(this.flowService.create(flow));
    }
  }

  private createFromForm(): IFlow {
    return {
      ...new Flow(),
      id: this.editForm.get(['id'])!.value,
      flowName: this.editForm.get(['flowName'])!.value,
      flowDescription: this.editForm.get(['flowDescription'])!.value,
      nextExecutionStartTime: this.editForm.get(['nextExecutionStartTime'])!.value
        ? moment(this.editForm.get(['nextExecutionStartTime'])!.value, DATE_TIME_FORMAT)
        : undefined,
      flowState: this.editForm.get(['flowState'])!.value,
      eventTrigger: this.editForm.get(['eventTrigger'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IFlow>>): void {
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

  trackById(index: number, item: IEventTrigger): any {
    return item.id;
  }
}
