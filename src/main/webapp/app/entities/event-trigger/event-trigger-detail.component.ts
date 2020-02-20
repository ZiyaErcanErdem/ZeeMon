import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEventTrigger } from 'app/shared/model/event-trigger.model';

@Component({
  selector: 'jhi-event-trigger-detail',
  templateUrl: './event-trigger-detail.component.html'
})
export class EventTriggerDetailComponent implements OnInit {
  eventTrigger: IEventTrigger | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ eventTrigger }) => (this.eventTrigger = eventTrigger));
  }

  previousState(): void {
    window.history.back();
  }
}
