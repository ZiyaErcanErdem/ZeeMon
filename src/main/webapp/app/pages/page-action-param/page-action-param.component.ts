import { Component, OnInit, OnDestroy } from '@angular/core';
import { BasePageComponent } from 'app/pages/base-page.component';
import { GenericDynamicAction, QueryMode, Theme, Condition, Operator, PageViewMode, DynamicService } from 'angular-dynamic-page';
import { AccountService } from 'app/core/auth/account.service';

import { ActionParam } from 'app/shared/model/action-param.model';
import { ActionScript } from 'app/shared/model/action-script.model';

@Component({
  selector: 'jhi-page-action-param',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageActionParamComponent extends BasePageComponent<ActionParam> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  constructor(dynamicService: DynamicService, accountService: AccountService) {
    super('ActionParam', dynamicService, accountService);
    this.setGridCols('id', 'actionScript.scriptName', 'actionScript.actionType', 'paramName', 'paramDataType', 'paramValue');
    this.setCompactCols('id', 'paramName');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(ActionScript, ActionParam)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 20;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        return config;
      })
      .withMetamodelConfiguration(cmd => {
        if (cmd.qualifier === 'ActionParam' || cmd.qualifier === 'ActionScript') {
          cmd.showWhenGrid = this.gridCols.includes(cmd.path);
          cmd.showWhenCompact = this.compactCols.includes(cmd.path);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }
      })
      .withRelationConfiguration(relation => {
        if (relation.qualifier === 'ActionParam') {
          relation.accessPath = 'action-params';
        } else if (relation.qualifier === 'ActionScript') {
          relation.accessPath = 'action-scripts';
          relation.descriptionColumnName = 'scriptName';
          relation.popupColumns = ['id', 'scriptName', 'scriptType', 'actionType'];
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query
          .withCondition(Condition.OR)
          .addPredicate('actionScript.scriptName', Operator.LIKE, '')
          .and()
          .addPredicate('paramName', Operator.LIKE, '')
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
