import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFlowExecution } from 'app/shared/model/flow-execution.model';

@Component({
  selector: 'jhi-flow-execution-detail',
  templateUrl: './flow-execution-detail.component.html'
})
export class FlowExecutionDetailComponent implements OnInit {
  flowExecution: IFlowExecution | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ flowExecution }) => (this.flowExecution = flowExecution));
  }

  previousState(): void {
    window.history.back();
  }
}
