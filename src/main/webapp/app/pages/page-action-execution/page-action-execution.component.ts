import { Component, OnInit, OnDestroy } from '@angular/core';
import { BasePageComponent } from 'app/pages/base-page.component';
import { GenericDynamicAction, QueryMode, Theme, Condition, Operator, PageViewMode, DynamicService } from 'angular-dynamic-page';
import { AccountService } from 'app/core/auth/account.service';

import { ActionExecution } from 'app/shared/model/action-execution.model';
import { Action } from 'app/shared/model/action.model';
import { Agent } from 'app/shared/model/agent.model';

@Component({
  selector: 'jhi-page-action-execution',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageActionExecutionComponent extends BasePageComponent<ActionExecution> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  constructor(dynamicService: DynamicService, accountService: AccountService) {
    super('ActionExecution', dynamicService, accountService);
    this.setGridCols(
      'id',
      'action.actionName',
      'executionStartTime',
      'executionEndTime',
      'executionStatus',
      'action.agent.agentInstanceId'
    );
    this.setCompactCols('id', 'action.actionName');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(Action, ActionExecution, Agent)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 20;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        return config;
      })
      .withMetamodelConfiguration(cmd => {
        if (cmd.qualifier === 'ActionExecution' || cmd.qualifier === 'Action' || cmd.qualifier === 'Agent') {
          cmd.showWhenGrid = this.gridCols.includes(cmd.path);
          cmd.showWhenCompact = this.compactCols.includes(cmd.path);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }
      })
      .withRelationConfiguration(relation => {
        if (relation.group === 'actionExecution') {
          relation.accessPath = 'action-executions';
        } else if (relation.group === 'action') {
          relation.accessPath = 'actions';
          relation.descriptionColumnName = 'actionName';
          relation.popupColumns = ['id', 'actionName', 'actionState'];
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query
          .withCondition(Condition.OR)
          .addPredicate('action.actionName', Operator.LIKE, '')
          .and()
          .addPredicate('action.agent.agentInstanceId', Operator.LIKE, '')
          .and();
      })
      .withViewer(PageViewMode.EDITOR);
  }

  ngDestroy(): void {
    this.registeredActions.forEach(a => this.pageManager!.unregisterAction(a));
    this.registeredActions = [];
    super.ngOnDestroy();
  }
}
