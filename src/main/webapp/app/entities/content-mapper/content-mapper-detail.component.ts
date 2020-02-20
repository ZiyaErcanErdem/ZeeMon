import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IContentMapper } from 'app/shared/model/content-mapper.model';

@Component({
  selector: 'jhi-content-mapper-detail',
  templateUrl: './content-mapper-detail.component.html'
})
export class ContentMapperDetailComponent implements OnInit {
  contentMapper: IContentMapper | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentMapper }) => (this.contentMapper = contentMapper));
  }

  previousState(): void {
    window.history.back();
  }
}
