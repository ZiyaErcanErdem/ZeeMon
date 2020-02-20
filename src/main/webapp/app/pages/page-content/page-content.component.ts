import { Component, OnInit, OnDestroy } from '@angular/core';
import { BasePageComponent } from 'app/pages/base-page.component';
import { GenericDynamicAction, QueryMode, Theme, Condition, Operator, PageViewMode, DynamicService } from 'angular-dynamic-page';
import { AccountService } from 'app/core/auth/account.service';

import { Content } from 'app/shared/model/content.model';

@Component({
  selector: 'jhi-page-content',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageContentComponent extends BasePageComponent<Content> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  constructor(dynamicService: DynamicService, accountService: AccountService) {
    super('Content', dynamicService, accountService);
    this.setGridCols('id', 'checkScript.scriptName', 'txt1', 'txt2', 'txt3', 'num1', 'num2', 'num3', 'date1', 'date2', 'bool1');
    this.setCompactCols('id', 'txt1');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(Content)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 20;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        return config;
      })
      .withMetamodelConfiguration(cmd => {
        if (cmd.qualifier === 'Content' || cmd.qualifier === 'CheckScript') {
          cmd.showWhenGrid = this.gridCols.includes(cmd.path);
          cmd.showWhenCompact = this.compactCols.includes(cmd.path);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }
      })
      .withRelationConfiguration(relation => {
        if (relation.qualifier === 'Content') {
          relation.accessPath = 'contents';
        } else if (relation.qualifier === 'CheckScript') {
          relation.accessPath = 'check-scripts';
          relation.descriptionColumnName = 'scriptName';
          relation.popupColumns = ['id', 'scriptName', 'scriptType'];
        } else if (relation.qualifier === 'Flow') {
          relation.accessPath = 'flows';
          relation.descriptionColumnName = 'flowName';
          relation.popupColumns = ['id', 'flowName', 'flowState'];
        } else if (relation.qualifier === 'Task') {
          relation.accessPath = 'tasks';
          relation.descriptionColumnName = 'taskName';
          relation.popupColumns = ['id', 'taskName', 'taskState'];
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query
          .withCondition(Condition.OR)
          .addPredicate('checkScript.scriptName', Operator.LIKE, '')
          .and()
          .addPredicate('flow.flowName', Operator.LIKE, '')
          .and()
          .addPredicate('task.taskName', Operator.LIKE, '')
          .and()
          .addPredicate('txt1', Operator.LIKE, '')
          .and()
          .addPredicate('txt2', Operator.LIKE, '')
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
