import { Component, OnInit, OnDestroy, Input, Output, EventEmitter, Optional } from '@angular/core';
import { TranslateService } from '@ngx-translate/core';
import { IExecutionResult, IContentDto, IFieldMappingDto } from '../model/execution-result.model';
import { MaestroExecutionService } from '../maestro-execution.service';
import { ExecutionResultRequest } from '../model/execution-result-request.model';
import {
  GenericDynamicAction,
  Theme,
  TableFieldControl,
  TableField,
  DynamicActionBuilder,
  ActionScope,
  DynamicUtil,
  PopoverRef,
  ActionType
} from 'angular-dynamic-page';

@Component({
  selector: 'jhi-execution-content-view',
  template: `
    <zee-dynamic-table
      [control]="control"
      [theme]="theme"
      [title]="title | translate"
      (rowSelect)="rowSelected($event)"
    ></zee-dynamic-table>
  `,
  styles: [
    `
      :host {
        min-height: 200px;
        min-width: 700px;
      }
    `
  ]
})
export class ExecutionContentViewComponent implements OnInit, OnDestroy {
  @Input()
  taskExecutionId = 0;
  @Input()
  theme: Theme;
  @Input()
  title: string;

  @Output()
  itemSelect: EventEmitter<IContentDto> = new EventEmitter<IContentDto>();

  private set context(value: IExecutionResult | null) {
    this._context = value;
    this.setupColumnsAndSetData(this._context);
  }
  private get context(): IExecutionResult | null {
    return this._context;
  }

  private _context: IExecutionResult | null = null;

  protected refreshAction?: GenericDynamicAction<any>;
  protected filterAction?: GenericDynamicAction<any>;
  public control: TableFieldControl<IContentDto>;

  private actionFilters?: Array<string | number>;

  constructor(
    private maestroExecutionService: MaestroExecutionService,
    private translateService: TranslateService,
    @Optional() private popoverRef?: PopoverRef<{ taskExecutionId: number; theme: Theme }, any>
  ) {
    this.control = new TableFieldControl<IContentDto>('Content');
    // this.control.withI18nPrefix('evaGatewayApp.evaCoreAuditLog');
    this.theme = Theme.dark;
    this.title = 'zeemonApp.content.home.title';

    this.initIfPopover();
  }

  private initIfPopover(): void {
    if (this.popoverRef && this.popoverRef.context) {
      this.taskExecutionId = this.popoverRef.context.taskExecutionId;
      this.theme = this.popoverRef.context.theme ? this.popoverRef.context.theme : Theme.dark;
    }
  }

  ngOnInit(): void {
    this.buildTable();
    this.registerActions();

    if (this.taskExecutionId) {
      this.requestData(this.taskExecutionId);
    }
  }

  ngOnDestroy(): void {
    if (this.refreshAction) {
      this.refreshAction.destroy();
      this.refreshAction = undefined;
    }
    if (this.filterAction) {
      this.filterAction.destroy();
      this.filterAction = undefined;
    }
    if (this.control) {
      this.control.destroy();
    }
    this.actionFilters = undefined;
    this.popoverRef = undefined;
  }

  private buildTable(): void {
    this.control.addField(TableField.of('id', 'global.field.id', true, 'static')).withColWidth('65px');
    this.control.addField(TableField.of('sourceIndex', 'Source Index', false, 'static'));

    [...Array(20).keys()]
      .map(n => `txt${n + 1}`)
      .forEach(s => {
        this.control.addField(TableField.of(s, s, false, 'static'));
      });
    [...Array(20).keys()]
      .map(n => `num${n + 1}`)
      .forEach(s => {
        this.control.addField(TableField.of(s, s, false, 'static'));
      });
    [...Array(10).keys()]
      .map(n => `date${n + 1}`)
      .forEach(s => {
        this.control.addField(TableField.of(s, s, false, 'static'));
      });
    [...Array(5).keys()]
      .map(n => `bool${n + 1}`)
      .forEach(s => {
        this.control.addField(TableField.of(s, s, false, 'static'));
      });

    this.control.addField(TableField.of('checkScriptId', 'checkScriptId', false, 'static')).withColWidth('65px');
    this.control.addField(TableField.of('flowId', 'flowId', false, 'static')).withColWidth('65px');
    this.control.addField(TableField.of('taskId', 'taskId', false, 'static')).withColWidth('65px');
    this.control.addField(TableField.of('taskExecutionId', 'taskExecutionId', false, 'static')).withColWidth('65px');
    this.control.addField(TableField.of('flowExecutionId', 'flowExecutionId', false, 'static')).withColWidth('65px');

    this.control.readonlyAll(true);
    this.control.hideAll();
    this.control.show('id');
  }

  private readValue(row: IContentDto, val: string): string {
    if (!val) {
      return val;
    }
    return val;
  }

  private setupColumnsAndSetData(rs: IExecutionResult | null): void {
    if (!this.control) {
      return;
    }

    this.control.hideAll();
    this.control.show('id');
    this.control.readonlyAll(true);

    if (!rs || !rs.mappings) {
      this.control.data = rs!.content || [];
      return;
    }

    rs.mappings.forEach(m => {
      this.setupColumn(m);
    });

    this.control.data = rs.content ? rs.content : [];
  }

  private setupColumn(m: IFieldMappingDto): void {
    if (!m || !m.targetColName || !m.targetName) {
      return;
    }
    this.control.setLabel(m.targetColName, m.targetName);
    this.control.show(m.targetColName);
    this.control.readonly(m.targetColName, true);
  }

  private readCheckScriptType(row: IContentDto): string {
    if (!row || !row.checkScriptId) {
      return '';
    }
    return this.translate('evaGatewayApp.LogType.' + row.checkScriptId);
  }

  private translate(label: string): string {
    return this.translateService.instant(label);
  }

  private requestData(taskExecutionId: number, contentOnly = false): void {
    const request: ExecutionResultRequest = new ExecutionResultRequest();
    request.taskExecutionId = taskExecutionId;
    request.contentOnly = contentOnly;
    this.maestroExecutionService.prepareExecutionResult(request).subscribe(
      response => {
        const rs = response.body;
        if (contentOnly && this.context) {
          this.context.content = rs!.content;
          this.control.data = rs!.content || [];
        } else {
          this.context = response.body;
        }
        this.checkActionState();
      },
      () => this.checkActionState()
    );
  }

  private registerActions(): void {
    this.refreshAction = new DynamicActionBuilder<any>('refresh', ActionType.LIST)
      .withScope(ActionScope.CUSTOM)
      .withLabel('dynamic.action.refresh')
      .withButtonClass(DynamicUtil.buttonThemeFor(this.theme))
      .withIconClass('sync-alt')
      .withHandler(comp => {
        comp.disabled = true;
        this.requestData(this.taskExecutionId, true);
      })
      .build();
    this.control.addAction(this.refreshAction);

    this.checkActionState();
  }

  private checkActionState(): void {
    if (this.refreshAction) {
      this.refreshAction.disabled = false;
    }
  }

  public rowSelected(row: IContentDto): void {
    if (row) {
      // console.log('row selected: ' + row.id);
    }
    this.checkActionState();
    this.itemSelect.emit(row);
  }
}
