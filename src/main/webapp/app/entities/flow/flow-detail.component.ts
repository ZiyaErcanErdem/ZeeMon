import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IFlow } from 'app/shared/model/flow.model';

@Component({
  selector: 'jhi-flow-detail',
  templateUrl: './flow-detail.component.html'
})
export class FlowDetailComponent implements OnInit {
  flow: IFlow | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ flow }) => (this.flow = flow));
  }

  previousState(): void {
    window.history.back();
  }
}
