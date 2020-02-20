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
  PageRelation
} from 'angular-dynamic-page';
import { AccountService } from 'app/core/auth/account.service';

import { Flow } from 'app/shared/model/flow.model';
import { EventTrigger } from 'app/shared/model/event-trigger.model';
import { Task } from 'app/shared/model/task.model';
import { FlowExecution } from 'app/shared/model/flow-execution.model';

@Component({
  selector: 'jhi-page-flow',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageFlowComponent extends BasePageComponent<Flow> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  constructor(dynamicService: DynamicService, accountService: AccountService) {
    super('Flow', dynamicService, accountService);
    this.setGridCols('id', 'flowName', 'nextExecutionStartTime', 'flowState', 'eventTrigger.triggerName', 'eventTrigger.triggerType');
    this.setCompactCols('id', 'flowName');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(Flow, EventTrigger)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 20;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        return config;
      })
      .withMetamodelConfiguration(cmd => {
        if (cmd.qualifier === 'Flow' || cmd.qualifier === 'EventTrigger') {
          cmd.showWhenGrid = this.gridCols.includes(cmd.path);
          cmd.showWhenCompact = this.compactCols.includes(cmd.path);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }
      })
      .withRelationConfiguration(relation => {
        if (relation.qualifier === 'Flow') {
          relation.accessPath = 'flows';
        } else if (relation.qualifier === 'EventTrigger') {
          relation.accessPath = 'event-triggers';
          relation.descriptionColumnName = 'triggerName';
          relation.popupColumns = ['id', 'triggerName', 'triggerType'];
        } else if (relation.qualifier === 'Task') {
          relation.accessPath = 'tasks';
          relation.descriptionColumnName = 'taskName';
          this.configureTaskRelation(relation);
        } else if (relation.qualifier === 'FlowExecution') {
          relation.accessPath = 'flow-executions';
          relation.descriptionColumnName = 'flowName';
          this.configureFlowExecutionRelation(relation);
        } else {
          relation.searchable = false;
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query
          .withCondition(Condition.OR)
          .addPredicate('flowName', Operator.LIKE, '')
          .and()
          .addPredicate('eventTrigger.triggerName', Operator.LIKE, '')
          .and();
      })
      .withViewer(PageViewMode.EDITOR);
  }

  private configureFlowExecutionRelation(relation: PageRelation): void {
    this.pageManager!.createRelationPageManager(relation)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 50;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        config.canCreate = false;
        config.canEdit = false;
        config.canDelete = false;
        return config;
      })
      .withGridColumns('id', 'executionStartTime', 'executionEndTime', 'executionStatus')
      .withCompactColumns('id', 'executionStartTime')
      .withSortingSamples(FlowExecution)
      .withRelationConfiguration((pb, rel) => {
        if (rel.qualifier === 'Flow') {
          rel.accessPath = 'flows';
          rel.descriptionColumnName = 'flowName';
          rel.popupColumns = ['id', 'flowName', 'flowState', 'nextExecutionStartTime'];
        }
        return rel;
      })
      .withViewer(PageViewMode.DETAIL);
  }

  private configureTaskRelation(relation: PageRelation): void {
    this.pageManager!.createRelationPageManager(relation)
      .withGridColumns('id', 'taskName', 'taskState', 'checkScript.scriptName', 'flow.flowName', 'agent.agentName')
      .withCompactColumns('id', 'taskName')
      .withSortingSamples(Task)
      .withRelationConfiguration((pb, rel) => {
        if (rel.qualifier === 'CheckScript') {
          rel.accessPath = 'check-scripts';
          rel.descriptionColumnName = 'scriptName';
          rel.popupColumns = ['id', 'scriptName', 'scriptType'];
        } else if (rel.qualifier === 'Flow') {
          rel.accessPath = 'flows';
          rel.descriptionColumnName = 'flowName';
          rel.popupColumns = ['id', 'flowName', 'flowState'];
        } else if (rel.qualifier === 'Agent') {
          rel.accessPath = 'agent';
          rel.descriptionColumnName = 'agentName';
          rel.popupColumns = ['id', 'agentName', 'agentStatus'];
        }
        return relation;
      });
  }

  ngDestroy(): void {
    this.registeredActions.forEach(a => this.pageManager!.unregisterAction(a));
    this.registeredActions = [];
    super.ngOnDestroy();
  }
}
