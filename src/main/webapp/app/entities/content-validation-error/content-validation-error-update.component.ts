import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';

import { IContentValidationError, ContentValidationError } from 'app/shared/model/content-validation-error.model';
import { ContentValidationErrorService } from './content-validation-error.service';
import { ITask } from 'app/shared/model/task.model';
import { TaskService } from 'app/entities/task/task.service';
import { ITaskExecution } from 'app/shared/model/task-execution.model';
import { TaskExecutionService } from 'app/entities/task-execution/task-execution.service';

type SelectableEntity = ITask | ITaskExecution;

@Component({
  selector: 'jhi-content-validation-error-update',
  templateUrl: './content-validation-error-update.component.html'
})
export class ContentValidationErrorUpdateComponent implements OnInit {
  isSaving = false;
  tasks: ITask[] = [];
  taskexecutions: ITaskExecution[] = [];

  editForm = this.fb.group({
    id: [],
    sourceIndex: [null, [Validators.required, Validators.min(0)]],
    sourceText: [null, [Validators.maxLength(2048)]],
    errorText: [null, [Validators.maxLength(2048)]],
    task: [null, Validators.required],
    taskExecution: [null, Validators.required]
  });

  constructor(
    protected contentValidationErrorService: ContentValidationErrorService,
    protected taskService: TaskService,
    protected taskExecutionService: TaskExecutionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ contentValidationError }) => {
      this.updateForm(contentValidationError);

      this.taskService.query().subscribe((res: HttpResponse<ITask[]>) => (this.tasks = res.body || []));

      this.taskExecutionService.query().subscribe((res: HttpResponse<ITaskExecution[]>) => (this.taskexecutions = res.body || []));
    });
  }

  updateForm(contentValidationError: IContentValidationError): void {
    this.editForm.patchValue({
      id: contentValidationError.id,
      sourceIndex: contentValidationError.sourceIndex,
      sourceText: contentValidationError.sourceText,
      errorText: contentValidationError.errorText,
      task: contentValidationError.task,
      taskExecution: contentValidationError.taskExecution
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const contentValidationError = this.createFromForm();
    if (contentValidationError.id !== undefined) {
      this.subscribeToSaveResponse(this.contentValidationErrorService.update(contentValidationError));
    } else {
      this.subscribeToSaveResponse(this.contentValidationErrorService.create(contentValidationError));
    }
  }

  private createFromForm(): IContentValidationError {
    return {
      ...new ContentValidationError(),
      id: this.editForm.get(['id'])!.value,
      sourceIndex: this.editForm.get(['sourceIndex'])!.value,
      sourceText: this.editForm.get(['sourceText'])!.value,
      errorText: this.editForm.get(['errorText'])!.value,
      task: this.editForm.get(['task'])!.value,
      taskExecution: this.editForm.get(['taskExecution'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContentValidationError>>): void {
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

  trackById(index: number, item: SelectableEntity): any {
    return item.id;
  }
}
