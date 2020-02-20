import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEventTrigger, EventTrigger } from 'app/shared/model/event-trigger.model';
import { EventTriggerService } from './event-trigger.service';

@Component({
  selector: 'jhi-event-trigger-update',
  templateUrl: './event-trigger-update.component.html'
})
export class EventTriggerUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    triggerName: [null, [Validators.required, Validators.maxLength(100)]],
    triggerType: [null, [Validators.required]],
    triggerPeriod: [],
    triggerTimeUnit: [],
    triggerCronExpression: [null, [Validators.maxLength(255)]],
    triggerCronTimeZone: [null, [Validators.maxLength(255)]]
  });

  constructor(protected eventTriggerService: EventTriggerService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventTrigger }) => {
      this.updateForm(eventTrigger);
    });
  }

  updateForm(eventTrigger: IEventTrigger): void {
    this.editForm.patchValue({
      id: eventTrigger.id,
      triggerName: eventTrigger.triggerName,
      triggerType: eventTrigger.triggerType,
      triggerPeriod: eventTrigger.triggerPeriod,
      triggerTimeUnit: eventTrigger.triggerTimeUnit,
      triggerCronExpression: eventTrigger.triggerCronExpression,
      triggerCronTimeZone: eventTrigger.triggerCronTimeZone
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const eventTrigger = this.createFromForm();
    if (eventTrigger.id !== undefined) {
      this.subscribeToSaveResponse(this.eventTriggerService.update(eventTrigger));
    } else {
      this.subscribeToSaveResponse(this.eventTriggerService.create(eventTrigger));
    }
  }

  private createFromForm(): IEventTrigger {
    return {
      ...new EventTrigger(),
      id: this.editForm.get(['id'])!.value,
      triggerName: this.editForm.get(['triggerName'])!.value,
      triggerType: this.editForm.get(['triggerType'])!.value,
      triggerPeriod: this.editForm.get(['triggerPeriod'])!.value,
      triggerTimeUnit: this.editForm.get(['triggerTimeUnit'])!.value,
      triggerCronExpression: this.editForm.get(['triggerCronExpression'])!.value,
      triggerCronTimeZone: this.editForm.get(['triggerCronTimeZone'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEventTrigger>>): void {
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
}
