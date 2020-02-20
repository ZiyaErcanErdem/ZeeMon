import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEndpointProperty, EndpointProperty } from 'app/shared/model/endpoint-property.model';
import { EndpointPropertyService } from './endpoint-property.service';
import { IEndpoint } from 'app/shared/model/endpoint.model';
import { EndpointService } from 'app/entities/endpoint/endpoint.service';

@Component({
  selector: 'jhi-endpoint-property-update',
  templateUrl: './endpoint-property-update.component.html'
})
export class EndpointPropertyUpdateComponent implements OnInit {
  isSaving = false;
  endpoints: IEndpoint[] = [];

  editForm = this.fb.group({
    id: [],
    propKey: [null, [Validators.required, Validators.maxLength(100)]],
    propKeyType: [null, [Validators.required]],
    propValue: [null, [Validators.required, Validators.maxLength(512)]],
    propValueType: [null, [Validators.required]],
    propDescription: [null, [Validators.maxLength(512)]],
    endpoint: [null, Validators.required]
  });

  constructor(
    protected endpointPropertyService: EndpointPropertyService,
    protected endpointService: EndpointService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endpointProperty }) => {
      this.updateForm(endpointProperty);

      this.endpointService.query().subscribe((res: HttpResponse<IEndpoint[]>) => (this.endpoints = res.body || []));
    });
  }

  updateForm(endpointProperty: IEndpointProperty): void {
    this.editForm.patchValue({
      id: endpointProperty.id,
      propKey: endpointProperty.propKey,
      propKeyType: endpointProperty.propKeyType,
      propValue: endpointProperty.propValue,
      propValueType: endpointProperty.propValueType,
      propDescription: endpointProperty.propDescription,
      endpoint: endpointProperty.endpoint
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const endpointProperty = this.createFromForm();
    if (endpointProperty.id !== undefined) {
      this.subscribeToSaveResponse(this.endpointPropertyService.update(endpointProperty));
    } else {
      this.subscribeToSaveResponse(this.endpointPropertyService.create(endpointProperty));
    }
  }

  private createFromForm(): IEndpointProperty {
    return {
      ...new EndpointProperty(),
      id: this.editForm.get(['id'])!.value,
      propKey: this.editForm.get(['propKey'])!.value,
      propKeyType: this.editForm.get(['propKeyType'])!.value,
      propValue: this.editForm.get(['propValue'])!.value,
      propValueType: this.editForm.get(['propValueType'])!.value,
      propDescription: this.editForm.get(['propDescription'])!.value,
      endpoint: this.editForm.get(['endpoint'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndpointProperty>>): void {
    result.subscribe(
      () => this.onSaveSuccess(),
      () => this.onSaveError()
    );
  }

  protected onSaveSuccess(): void {
    this.isSaving = false;
    this.previousState();
  }

  protected onSaveError(): void {
    this.isSaving = false;
  }

  trackById(index: number, item: IEndpoint): any {
    return item.id;
  }
}
