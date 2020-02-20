import { Component, OnInit } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
// eslint-disable-next-line @typescript-eslint/no-unused-vars
import { FormBuilder, Validators } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_TIME_FORMAT } from 'app/shared/constants/input.constants';

import { IContent, Content } from 'app/shared/model/content.model';
import { ContentService } from './content.service';
import { ICheckScript } from 'app/shared/model/check-script.model';
import { CheckScriptService } from 'app/entities/check-script/check-script.service';
import { IFlow } from 'app/shared/model/flow.model';
import { FlowService } from 'app/entities/flow/flow.service';
import { ITask } from 'app/shared/model/task.model';
import { TaskService } from 'app/entities/task/task.service';
import { ITaskExecution } from 'app/shared/model/task-execution.model';
import { TaskExecutionService } from 'app/entities/task-execution/task-execution.service';
import { IFlowExecution } from 'app/shared/model/flow-execution.model';
import { FlowExecutionService } from 'app/entities/flow-execution/flow-execution.service';

type SelectableEntity = ICheckScript | IFlow | ITask | ITaskExecution | IFlowExecution;

@Component({
  selector: 'jhi-content-update',
  templateUrl: './content-update.component.html'
})
export class ContentUpdateComponent implements OnInit {
  isSaving = false;
  checkscripts: ICheckScript[] = [];
  flows: IFlow[] = [];
  tasks: ITask[] = [];
  taskexecutions: ITaskExecution[] = [];
  flowexecutions: IFlowExecution[] = [];

  editForm = this.fb.group({
    id: [],
    sourceIndex: [null, [Validators.required, Validators.min(0)]],
    txt1: [null, [Validators.maxLength(255)]],
    txt2: [null, [Validators.maxLength(255)]],
    txt3: [null, [Validators.maxLength(255)]],
    txt4: [null, [Validators.maxLength(255)]],
    txt5: [null, [Validators.maxLength(255)]],
    txt6: [null, [Validators.maxLength(255)]],
    txt7: [null, [Validators.maxLength(255)]],
    txt8: [null, [Validators.maxLength(255)]],
    txt9: [null, [Validators.maxLength(255)]],
    txt10: [null, [Validators.maxLength(255)]],
    txt11: [null, [Validators.maxLength(255)]],
    txt12: [null, [Validators.maxLength(255)]],
    txt13: [null, [Validators.maxLength(255)]],
    txt14: [null, [Validators.maxLength(255)]],
    txt15: [null, [Validators.maxLength(255)]],
    txt16: [null, [Validators.maxLength(255)]],
    txt17: [null, [Validators.maxLength(255)]],
    txt18: [null, [Validators.maxLength(255)]],
    txt19: [null, [Validators.maxLength(255)]],
    txt20: [null, [Validators.maxLength(255)]],
    num1: [],
    num2: [],
    num3: [],
    num4: [],
    num5: [],
    num6: [],
    num7: [],
    num8: [],
    num9: [],
    num10: [],
    num11: [],
    num12: [],
    num13: [],
    num14: [],
    num15: [],
    num16: [],
    num17: [],
    num18: [],
    num19: [],
    num20: [],
    date1: [],
    date2: [],
    date3: [],
    date4: [],
    date5: [],
    date6: [],
    date7: [],
    date8: [],
    date9: [],
    date10: [],
    bool1: [],
    bool2: [],
    bool3: [],
    bool4: [],
    bool5: [],
    checkScript: [null, Validators.required],
    flow: [null, Validators.required],
    task: [null, Validators.required],
    taskExecution: [null, Validators.required],
    flowExecution: [null, Validators.required]
  });

  constructor(
    protected contentService: ContentService,
    protected checkScriptService: CheckScriptService,
    protected flowService: FlowService,
    protected taskService: TaskService,
    protected taskExecutionService: TaskExecutionService,
    protected flowExecutionService: FlowExecutionService,
    protected activatedRoute: ActivatedRoute,
    private fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.activatedRoute.data.subscribe(({ content }) => {
      if (!content.id) {
        const today = moment().startOf('day');
        content.date1 = today;
        content.date2 = today;
        content.date3 = today;
        content.date4 = today;
        content.date5 = today;
        content.date6 = today;
        content.date7 = today;
        content.date8 = today;
        content.date9 = today;
        content.date10 = today;
        content.bool1 = today;
        content.bool2 = today;
        content.bool3 = today;
        content.bool4 = today;
        content.bool5 = today;
      }

      this.updateForm(content);

      this.checkScriptService.query().subscribe((res: HttpResponse<ICheckScript[]>) => (this.checkscripts = res.body || []));

      this.flowService.query().subscribe((res: HttpResponse<IFlow[]>) => (this.flows = res.body || []));

      this.taskService.query().subscribe((res: HttpResponse<ITask[]>) => (this.tasks = res.body || []));

      this.taskExecutionService.query().subscribe((res: HttpResponse<ITaskExecution[]>) => (this.taskexecutions = res.body || []));

      this.flowExecutionService.query().subscribe((res: HttpResponse<IFlowExecution[]>) => (this.flowexecutions = res.body || []));
    });
  }

  updateForm(content: IContent): void {
    this.editForm.patchValue({
      id: content.id,
      sourceIndex: content.sourceIndex,
      txt1: content.txt1,
      txt2: content.txt2,
      txt3: content.txt3,
      txt4: content.txt4,
      txt5: content.txt5,
      txt6: content.txt6,
      txt7: content.txt7,
      txt8: content.txt8,
      txt9: content.txt9,
      txt10: content.txt10,
      txt11: content.txt11,
      txt12: content.txt12,
      txt13: content.txt13,
      txt14: content.txt14,
      txt15: content.txt15,
      txt16: content.txt16,
      txt17: content.txt17,
      txt18: content.txt18,
      txt19: content.txt19,
      txt20: content.txt20,
      num1: content.num1,
      num2: content.num2,
      num3: content.num3,
      num4: content.num4,
      num5: content.num5,
      num6: content.num6,
      num7: content.num7,
      num8: content.num8,
      num9: content.num9,
      num10: content.num10,
      num11: content.num11,
      num12: content.num12,
      num13: content.num13,
      num14: content.num14,
      num15: content.num15,
      num16: content.num16,
      num17: content.num17,
      num18: content.num18,
      num19: content.num19,
      num20: content.num20,
      date1: content.date1 ? content.date1.format(DATE_TIME_FORMAT) : null,
      date2: content.date2 ? content.date2.format(DATE_TIME_FORMAT) : null,
      date3: content.date3 ? content.date3.format(DATE_TIME_FORMAT) : null,
      date4: content.date4 ? content.date4.format(DATE_TIME_FORMAT) : null,
      date5: content.date5 ? content.date5.format(DATE_TIME_FORMAT) : null,
      date6: content.date6 ? content.date6.format(DATE_TIME_FORMAT) : null,
      date7: content.date7 ? content.date7.format(DATE_TIME_FORMAT) : null,
      date8: content.date8 ? content.date8.format(DATE_TIME_FORMAT) : null,
      date9: content.date9 ? content.date9.format(DATE_TIME_FORMAT) : null,
      date10: content.date10 ? content.date10.format(DATE_TIME_FORMAT) : null,
      bool1: content.bool1 ? content.bool1.format(DATE_TIME_FORMAT) : null,
      bool2: content.bool2 ? content.bool2.format(DATE_TIME_FORMAT) : null,
      bool3: content.bool3 ? content.bool3.format(DATE_TIME_FORMAT) : null,
      bool4: content.bool4 ? content.bool4.format(DATE_TIME_FORMAT) : null,
      bool5: content.bool5 ? content.bool5.format(DATE_TIME_FORMAT) : null,
      checkScript: content.checkScript,
      flow: content.flow,
      task: content.task,
      taskExecution: content.taskExecution,
      flowExecution: content.flowExecution
    });
  }

  previousState(): void {
    window.history.back();
  }

  save(): void {
    this.isSaving = true;
    const content = this.createFromForm();
    if (content.id !== undefined) {
      this.subscribeToSaveResponse(this.contentService.update(content));
    } else {
      this.subscribeToSaveResponse(this.contentService.create(content));
    }
  }

  private createFromForm(): IContent {
    return {
      ...new Content(),
      id: this.editForm.get(['id'])!.value,
      sourceIndex: this.editForm.get(['sourceIndex'])!.value,
      txt1: this.editForm.get(['txt1'])!.value,
      txt2: this.editForm.get(['txt2'])!.value,
      txt3: this.editForm.get(['txt3'])!.value,
      txt4: this.editForm.get(['txt4'])!.value,
      txt5: this.editForm.get(['txt5'])!.value,
      txt6: this.editForm.get(['txt6'])!.value,
      txt7: this.editForm.get(['txt7'])!.value,
      txt8: this.editForm.get(['txt8'])!.value,
      txt9: this.editForm.get(['txt9'])!.value,
      txt10: this.editForm.get(['txt10'])!.value,
      txt11: this.editForm.get(['txt11'])!.value,
      txt12: this.editForm.get(['txt12'])!.value,
      txt13: this.editForm.get(['txt13'])!.value,
      txt14: this.editForm.get(['txt14'])!.value,
      txt15: this.editForm.get(['txt15'])!.value,
      txt16: this.editForm.get(['txt16'])!.value,
      txt17: this.editForm.get(['txt17'])!.value,
      txt18: this.editForm.get(['txt18'])!.value,
      txt19: this.editForm.get(['txt19'])!.value,
      txt20: this.editForm.get(['txt20'])!.value,
      num1: this.editForm.get(['num1'])!.value,
      num2: this.editForm.get(['num2'])!.value,
      num3: this.editForm.get(['num3'])!.value,
      num4: this.editForm.get(['num4'])!.value,
      num5: this.editForm.get(['num5'])!.value,
      num6: this.editForm.get(['num6'])!.value,
      num7: this.editForm.get(['num7'])!.value,
      num8: this.editForm.get(['num8'])!.value,
      num9: this.editForm.get(['num9'])!.value,
      num10: this.editForm.get(['num10'])!.value,
      num11: this.editForm.get(['num11'])!.value,
      num12: this.editForm.get(['num12'])!.value,
      num13: this.editForm.get(['num13'])!.value,
      num14: this.editForm.get(['num14'])!.value,
      num15: this.editForm.get(['num15'])!.value,
      num16: this.editForm.get(['num16'])!.value,
      num17: this.editForm.get(['num17'])!.value,
      num18: this.editForm.get(['num18'])!.value,
      num19: this.editForm.get(['num19'])!.value,
      num20: this.editForm.get(['num20'])!.value,
      date1: this.editForm.get(['date1'])!.value ? moment(this.editForm.get(['date1'])!.value, DATE_TIME_FORMAT) : undefined,
      date2: this.editForm.get(['date2'])!.value ? moment(this.editForm.get(['date2'])!.value, DATE_TIME_FORMAT) : undefined,
      date3: this.editForm.get(['date3'])!.value ? moment(this.editForm.get(['date3'])!.value, DATE_TIME_FORMAT) : undefined,
      date4: this.editForm.get(['date4'])!.value ? moment(this.editForm.get(['date4'])!.value, DATE_TIME_FORMAT) : undefined,
      date5: this.editForm.get(['date5'])!.value ? moment(this.editForm.get(['date5'])!.value, DATE_TIME_FORMAT) : undefined,
      date6: this.editForm.get(['date6'])!.value ? moment(this.editForm.get(['date6'])!.value, DATE_TIME_FORMAT) : undefined,
      date7: this.editForm.get(['date7'])!.value ? moment(this.editForm.get(['date7'])!.value, DATE_TIME_FORMAT) : undefined,
      date8: this.editForm.get(['date8'])!.value ? moment(this.editForm.get(['date8'])!.value, DATE_TIME_FORMAT) : undefined,
      date9: this.editForm.get(['date9'])!.value ? moment(this.editForm.get(['date9'])!.value, DATE_TIME_FORMAT) : undefined,
      date10: this.editForm.get(['date10'])!.value ? moment(this.editForm.get(['date10'])!.value, DATE_TIME_FORMAT) : undefined,
      bool1: this.editForm.get(['bool1'])!.value ? moment(this.editForm.get(['bool1'])!.value, DATE_TIME_FORMAT) : undefined,
      bool2: this.editForm.get(['bool2'])!.value ? moment(this.editForm.get(['bool2'])!.value, DATE_TIME_FORMAT) : undefined,
      bool3: this.editForm.get(['bool3'])!.value ? moment(this.editForm.get(['bool3'])!.value, DATE_TIME_FORMAT) : undefined,
      bool4: this.editForm.get(['bool4'])!.value ? moment(this.editForm.get(['bool4'])!.value, DATE_TIME_FORMAT) : undefined,
      bool5: this.editForm.get(['bool5'])!.value ? moment(this.editForm.get(['bool5'])!.value, DATE_TIME_FORMAT) : undefined,
      checkScript: this.editForm.get(['checkScript'])!.value,
      flow: this.editForm.get(['flow'])!.value,
      task: this.editForm.get(['task'])!.value,
      taskExecution: this.editForm.get(['taskExecution'])!.value,
      flowExecution: this.editForm.get(['flowExecution'])!.value
    };
  }

  protected subscribeToSaveResponse(result: Observable<HttpResponse<IContent>>): void {
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
