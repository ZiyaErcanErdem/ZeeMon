import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { ICheckScript } from 'app/shared/model/check-script.model';

@Component({
  selector: 'jhi-check-script-detail',
  templateUrl: './check-script-detail.component.html'
})
export class CheckScriptDetailComponent implements OnInit {
  checkScript: ICheckScript | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ checkScript }) => (this.checkScript = checkScript));
  }

  previousState(): void {
    window.history.back();
  }
}
