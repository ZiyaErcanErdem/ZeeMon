import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IEndpoint, Endpoint } from 'app/shared/model/endpoint.model';
import { EndpointService } from './endpoint.service';

@Component({
  selector: 'jhi-endpoint-update',
  templateUrl: './endpoint-update.component.html'
})
export class EndpointUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    endpointName: [null, [Validators.required, Validators.maxLength(100)]],
    endpointInstanceId: [null, [Validators.maxLength(100)]],
    endpointType: [null, [Validators.required]],
    endpointSpec: [null, [Validators.required]],
    endpointDescription: [null, [Validators.maxLength(512)]]
  });

  constructor(protected endpointService: EndpointService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ endpoint }) => {
      this.updateForm(endpoint);
    });
  }

  updateForm(endpoint: IEndpoint): void {
    this.editForm.patchValue({
      id: endpoint.id,
      endpointName: endpoint.endpointName,
      endpointInstanceId: endpoint.endpointInstanceId,
      endpointType: endpoint.endpointType,
      endpointSpec: endpoint.endpointSpec,
      endpointDescription: endpoint.endpointDescription
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const endpoint = this.createFromForm();
    if (endpoint.id !== undefined) {
      this.subscribeToSaveResponse(this.endpointService.update(endpoint));
    } else {
      this.subscribeToSaveResponse(this.endpointService.create(endpoint));
    }
  }

  private createFromForm(): IEndpoint {
    return {
      ...new Endpoint(),
      id: this.editForm.get(['id'])!.value,
      endpointName: this.editForm.get(['endpointName'])!.value,
      endpointInstanceId: this.editForm.get(['endpointInstanceId'])!.value,
      endpointType: this.editForm.get(['endpointType'])!.value,
      endpointSpec: this.editForm.get(['endpointSpec'])!.value,
      endpointDescription: this.editForm.get(['endpointDescription'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IEndpoint>>): void {
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
}
