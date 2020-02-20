import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { IEndpointProperty } from 'app/shared/model/endpoint-property.model';

@Component({
  selector: 'jhi-endpoint-property-detail',
  templateUrl: './endpoint-property-detail.component.html'
})
export class EndpointPropertyDetailComponent implements OnInit {
  endpointProperty: IEndpointProperty | null = null;

  constructor(protected activatedRoute: ActivatedRoute) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endpointProperty }) => (this.endpointProperty = endpointProperty));
  }

  previousState(): void {
    window.history.back();
  }
}
