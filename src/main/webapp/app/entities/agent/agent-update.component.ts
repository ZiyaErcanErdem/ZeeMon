import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IAgent, Agent } from 'app/shared/model/agent.model';
import { AgentService } from './agent.service';

@Component({
  selector: 'jhi-agent-update',
  templateUrl: './agent-update.component.html'
})
export class AgentUpdateComponent implements OnInit {
  isSaving = false;

  editForm = this.fb.group({
    id: [],
    agentName: [null, [Validators.required, Validators.maxLength(100)]],
    agentInstanceId: [null, [Validators.maxLength(100)]],
    listenURI: [null, [Validators.maxLength(512)]],
    agentType: [null, [Validators.required]],
    agentStatus: [null, [Validators.required]],
    agentDescription: [null, [Validators.maxLength(512)]]
  });

  constructor(protected agentService: AgentService, protected activatedRoute: ActivatedRoute, private fb: FormBuilder) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ agent }) => {
      this.updateForm(agent);
    });
  }

  updateForm(agent: IAgent): void {
    this.editForm.patchValue({
      id: agent.id,
      agentName: agent.agentName,
      agentInstanceId: agent.agentInstanceId,
      listenURI: agent.listenURI,
      agentType: agent.agentType,
      agentStatus: agent.agentStatus,
      agentDescription: agent.agentDescription
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const agent = this.createFromForm();
    if (agent.id !== undefined) {
      this.subscribeToSaveResponse(this.agentService.update(agent));
    } else {
      this.subscribeToSaveResponse(this.agentService.create(agent));
    }
  }

  private createFromForm(): IAgent {
    return {
      ...new Agent(),
      id: this.editForm.get(['id'])!.value,
      agentName: this.editForm.get(['agentName'])!.value,
      agentInstanceId: this.editForm.get(['agentInstanceId'])!.value,
      listenURI: this.editForm.get(['listenURI'])!.value,
      agentType: this.editForm.get(['agentType'])!.value,
      agentStatus: this.editForm.get(['agentStatus'])!.value,
      agentDescription: this.editForm.get(['agentDescription'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IAgent>>): void {
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
