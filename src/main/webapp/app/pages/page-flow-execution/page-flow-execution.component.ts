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
  PageRelation,
  DynamicPopoverService,
  PageManager,
  DynamicActionBuilder,
  ActionType,
  ActionScope,
  DynamicUtil
} from 'angular-dynamic-page';
import { AccountService } from 'app/core/auth/account.service';

import { FlowExecution } from 'app/shared/model/flow-execution.model';
import { Flow } from 'app/shared/model/flow.model';
import { TaskExecution } from 'app/shared/model/task-execution.model';
import { ExecutionContentViewComponent } from 'app/shared/maestro/execution-content-view/execution-content-view.component';

@Component({
  selector: 'jhi-page-flow-execution',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageFlowExecutionComponent extends BasePageComponent<FlowExecution> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  private contentAction?: GenericDynamicAction<any>;
  private selectionTaskExecution?: TaskExecution;

  constructor(dynamicService: DynamicService, private popoverService: DynamicPopoverService, accountService: AccountService) {
    super('FlowExecution', dynamicService, accountService);
    this.setGridCols(
      'id',
      'flow.flowName',
      'executionStartTime',
      'executionEndTime',
      'totalTaskCount',
      'runningTaskCount',
      'errorTaskCount',
      'executionStatus'
    );
    this.setCompactCols('id', 'flow.flowName');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(Flow, FlowExecution)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 20;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        return config;
      })
      .withMetamodelConfiguration(cmd => {
        if (cmd.group === 'flowExecution' || cmd.group === 'flow') {
          cmd.showWhenGrid = this.gridCols.includes(cmd.path);
          cmd.showWhenCompact = this.compactCols.includes(cmd.path);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }
      })
      .withRelationConfiguration(relation => {
        if (relation.qualifier === 'FlowExecution') {
          relation.accessPath = 'flow-executions';
        } else if (relation.qualifier === 'Flow') {
          relation.accessPath = 'flows';
          relation.descriptionColumnName = 'flowName';
          relation.popupColumns = ['id', 'flowName'];
        } else if (relation.qualifier === 'TaskExecution') {
          relation.accessPath = 'task-executions';
          relation.descriptionColumnName = 'executionStartTime';

          this.attachTaskExecutionRelation(relation);
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query
          .withCondition(Condition.OR)
          .addPredicate('flow.flowName', Operator.LIKE, '')
          .and();
      })
      .withViewer(PageViewMode.EDITOR);
  }

  private attachTaskExecutionRelation(relation: PageRelation): void {
    this.pageManager!.createRelationPageManager(relation)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 50;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        config.canCreate = false;
        config.canEdit = false;
        config.canEdit = false;
        return config;
      })
      .withGridColumns('id', 'executionStartTime', 'executionEndTime', 'executionStatus')
      .withCompactColumns('id', 'executionStartTime')
      .withSortingSamples(TaskExecution)
      .withRelationConfiguration((pb, rel) => {
        if (rel.group === 'taskExecution') {
          rel.accessPath = 'task-executions';
          this.registerTaskExecutionActions(pb);
          this.collect = pb.dataSelectionChange().subscribe(() => {
            this.selectionTaskExecution = pb.getSelectedData();
            this.checkActionState();
          });
        } else if (rel.group === 'task') {
          rel.accessPath = 'tasks';
          rel.descriptionColumnName = 'taskName';
          rel.popupColumns = ['id', 'taskName'];
        } else if (rel.group === 'flowExecution') {
          rel.accessPath = 'flow-executions';
          rel.descriptionColumnName = 'executionStatus';
          rel.popupColumns = ['id', 'executionStatus', 'totalTaskCount', 'errorTaskCount', 'runningTaskCount'];
        }
        return relation;
      })
      .withViewer(PageViewMode.DETAIL);
  }

  private registerTaskExecutionActions(taskExecutionPageBuilder: PageManager<any>): void {
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

    taskExecutionPageBuilder.registerAction(this.contentAction);

    this.checkActionState();
  }

  public openExecutionContent(comp: GenericDynamicAction<any>): void {
    if (!this.selectionTaskExecution) {
      return;
    }

    const taskExecutionId = this.selectionTaskExecution.id;
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

  protected checkActionState(): void {
    if (this.contentAction) {
      this.contentAction.visible = !!this.selectionTaskExecution;
    }
  }

  ngDestroy(): void {
    this.registeredActions.forEach(a => this.pageManager!.unregisterAction(a));
    this.registeredActions = [];
    this.contentAction = undefined;
    super.ngOnDestroy();
  }
}
