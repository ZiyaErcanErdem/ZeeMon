import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IAction } from 'app/shared/model/action.model';

@Component({
  selector: 'jhi-action-detail',
  templateUrl: './action-detail.component.html'
})
export class ActionDetailComponent implements OnInit {
  action: IAction | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ action }) => (this.action = action));
  }

  previousState(): void {
    window.history.back();
  }
}
