import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IActionScript } from 'app/shared/model/action-script.model';

@Component({
  selector: 'jhi-action-script-detail',
  templateUrl: './action-script-detail.component.html'
})
export class ActionScriptDetailComponent implements OnInit {
  actionScript: IActionScript | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ actionScript }) => (this.actionScript = actionScript));
  }

  previousState(): void {
    window.history.back();
  }
}
