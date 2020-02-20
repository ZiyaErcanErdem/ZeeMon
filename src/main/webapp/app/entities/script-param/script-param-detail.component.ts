import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IScriptParam } from 'app/shared/model/script-param.model';

@Component({
  selector: 'jhi-script-param-detail',
  templateUrl: './script-param-detail.component.html'
})
export class ScriptParamDetailComponent implements OnInit {
  scriptParam: IScriptParam | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ scriptParam }) => (this.scriptParam = scriptParam));
  }

  previousState(): void {
    window.history.back();
  }
}
