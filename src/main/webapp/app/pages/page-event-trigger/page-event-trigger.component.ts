import { Component, OnInit, OnDestroy } from '@angular/core';
import { BasePageComponent } from 'app/pages/base-page.component';
import { GenericDynamicAction, QueryMode, Theme, Condition, Operator, PageViewMode, DynamicService } from 'angular-dynamic-page';
import { AccountService } from 'app/core/auth/account.service';

import { EventTrigger } from 'app/shared/model/event-trigger.model';

@Component({
  selector: 'jhi-page-event-trigger',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageEventTriggerComponent extends BasePageComponent<EventTrigger> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  constructor(dynamicService: DynamicService, accountService: AccountService) {
    super('EventTrigger', dynamicService, accountService);
    this.setGridCols('id', 'triggerName', 'triggerType', 'triggerPeriod', 'triggerTimeUnit', 'triggerCronExpression');
    this.setCompactCols('id', 'triggerName');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(EventTrigger)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 20;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        return config;
      })
      .withMetamodelConfiguration(cmd => {
        if (cmd.qualifier === 'EventTrigger') {
          cmd.showWhenGrid = this.gridCols.includes(cmd.path);
          cmd.showWhenCompact = this.compactCols.includes(cmd.path);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }
      })
      .withRelationConfiguration(relation => {
        if (relation.qualifier === 'EventTrigger') {
          relation.accessPath = 'event-triggers';
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query.withCondition(Condition.OR).addPredicate('triggerName', Operator.LIKE, '');
      })
      .withViewer(PageViewMode.EDITOR);
  }

  ngDestroy(): void {
    this.registeredActions.forEach(a => this.pageManager!.unregisterAction(a));
    this.registeredActions = [];
    super.ngOnDestroy();
  }
}
