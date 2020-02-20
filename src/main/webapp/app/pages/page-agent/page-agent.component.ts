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
  RelationType
} from 'angular-dynamic-page';
import { AccountService } from 'app/core/auth/account.service';

import { Agent } from 'app/shared/model/agent.model';

@Component({
  selector: 'jhi-page-agent',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageAgentComponent extends BasePageComponent<Agent> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  constructor(dynamicService: DynamicService, accountService: AccountService) {
    super('Agent', dynamicService, accountService);
    this.setGridCols('id', 'agentName', 'agentInstanceId', 'agentType', 'agentStatus');
    this.setCompactCols('id', 'agentInstanceId');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(Agent)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 20;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        return config;
      })
      .withMetamodelConfiguration(cmd => {
        if (cmd.relType === RelationType.SELF) {
          cmd.showWhenGrid = this.gridCols.includes(cmd.name);
          cmd.showWhenCompact = this.compactCols.includes(cmd.name);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }
      })
      .withRelationConfiguration(relation => {
        if (relation.qualifier === 'Agent') {
          relation.accessPath = 'agents';
        } else {
          if (relation.qualifier !== 'Task' && relation.qualifier !== 'Action') {
            relation.searchable = false;
          }
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query
          .withCondition(Condition.OR)
          .addPredicate('agentName', Operator.LIKE, '')
          .and()
          .addPredicate('agentInstanceId', Operator.LIKE, '')
          .and();
      })
      .withViewer(PageViewMode.EDITOR);
  }

  ngOnDestroy(): void {
    this.registeredActions.forEach(a => this.pageManager!.unregisterAction(a));
    this.registeredActions = [];
    super.ngOnDestroy();
  }
}
