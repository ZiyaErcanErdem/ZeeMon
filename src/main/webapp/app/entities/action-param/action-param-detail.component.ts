import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActionParam } from 'app/shared/model/action-param.model';

@Component({
  selector: 'jhi-action-param-detail',
  templateUrl: './action-param-detail.component.html'
})
export class ActionParamDetailComponent implements OnInit {
  actionParam: IActionParam | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actionParam }) => (this.actionParam = actionParam));
  }

  previousState(): void {
    window.history.back();
  }
}
