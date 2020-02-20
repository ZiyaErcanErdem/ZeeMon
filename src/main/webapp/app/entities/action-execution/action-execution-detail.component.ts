import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActionExecution } from 'app/shared/model/action-execution.model';

@Component({
  selector: 'jhi-action-execution-detail',
  templateUrl: './action-execution-detail.component.html'
})
export class ActionExecutionDetailComponent implements OnInit {
  actionExecution: IActionExecution | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actionExecution }) => (this.actionExecution = actionExecution));
  }

  previousState(): void {
    window.history.back();
  }
}
