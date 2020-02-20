import { Component, OnInit, OnDestroy } from '@angular/core';
import { BasePageComponent } from 'app/pages/base-page.component';
import {
  GenericDynamicAction,
  QueryMode,
  Theme,
  Condition,
  Operator,
  PageViewMode,
  DynamicService,
  DynamicPopoverService,
  DynamicActionBuilder,
  ActionType,
  ActionScope,
  DynamicUtil
} from 'angular-dynamic-page';
import { AccountService } from 'app/core/auth/account.service';

import { TaskExecution } from 'app/shared/model/task-execution.model';
import { Task } from 'app/shared/model/task.model';
import { ExecutionContentViewComponent } from 'app/shared/maestro/execution-content-view/execution-content-view.component';
import { HttpClient } from '@angular/common/http';

@Component({
  selector: 'jhi-page-task-execution',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageTaskExecutionComponent extends BasePageComponent<TaskExecution> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];
  private selection?: TaskExecution;
  private contentAction?: GenericDynamicAction<any>;

  constructor(
    private http: HttpClient,
    dynamicService: DynamicService,
    private popoverService: DynamicPopoverService,
    accountService: AccountService
  ) {
    super('TaskExecution', dynamicService, accountService);
    this.setGridCols('id', 'task.taskName', 'executionStartTime', 'executionEndTime', 'executionStatus');
    this.setCompactCols('id', 'task.taskName');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(Task, TaskExecution)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 20;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        return config;
      })
      .withMetamodelConfiguration(cmd => {
        if (cmd.qualifier === 'TaskExecution' || cmd.qualifier === 'Task') {
          cmd.showWhenGrid = this.gridCols.includes(cmd.path);
          cmd.showWhenCompact = this.compactCols.includes(cmd.path);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }
      })
      .withRelationConfiguration(relation => {
        if (relation.group === 'taskExecution') {
          relation.accessPath = 'task-executions';
        } else if (relation.group === 'task') {
          relation.accessPath = 'tasks';
          relation.descriptionColumnName = 'taskName';
          relation.popupColumns = ['id', 'taskName'];
        } else if (relation.group === 'flowExecution') {
          relation.accessPath = 'flow-executions';
          relation.descriptionColumnName = 'executionStatus';
          relation.popupColumns = ['id', 'executionStatus', 'totalTaskCount', 'errorTaskCount', 'runningTaskCount'];
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query
          .withCondition(Condition.OR)
          .addPredicate('task.taskName', Operator.LIKE, '')
          .and();
      })
      .withViewer(PageViewMode.EDITOR);

    this.collect = this.pageManager!.ready().subscribe(isReady => {
      if (isReady) {
        this.registerActions();
      }
    });

    this.collect = this.pageManager!.dataSelectionChange().subscribe(() => {
      this.selection = this.pageManager!.getSelectedData();
      this.checkActionState();
    });
  }

  public openExecutionContent(comp: GenericDynamicAction<any>): void {
    if (!this.selection) {
      return;
    }

    const taskExecutionId = this.selection.id;
    const theme = this.theme;
    const context: { taskExecutionId?: number; theme: Theme } = { taskExecutionId, theme };
    const ref = this.popoverService.openDialog<{ taskExecutionId?: number; theme: Theme }, any>(ExecutionContentViewComponent, context, {
      maxWidth: '900px'
    });

    ref.afterClosed$.subscribe(() => {
      // console.log('ExecutionResult dialog is closed => ' + (result ? result.type + ':' + result.data : ''));
      comp.disabled = false;
    });
  }

  private registerAction(action: GenericDynamicAction<any>): boolean {
    if (this.pageManager!.registerAction(action)) {
      this.registeredActions.push(action);
      return true;
    }
    return false;
  }

  private registerActions(): void {
    this.contentAction = new DynamicActionBuilder<any>('extension.action.content', ActionType.CUSTOM)
      .withScope(ActionScope.EDITOR)
      .withLabel('extension.action.content')
      .withButtonClass(DynamicUtil.themeFor('btn-', Theme.primary))
      .withIconClass('tasks')
      .withHandler(comp => {
        comp.disabled = true;
        this.openExecutionContent(comp);
      })
      .build();
    this.registerAction(this.contentAction);
    this.checkActionState();
  }

  protected checkActionState(): void {
    if (this.contentAction) {
      this.contentAction.visible = !!this.selection;
    }
  }

  ngDestroy(): void {
    this.registeredActions.forEach(a => this.pageManager!.unregisterAction(a));
    this.registeredActions = [];
    this.contentAction = undefined;
    super.ngOnDestroy();
  }
}
