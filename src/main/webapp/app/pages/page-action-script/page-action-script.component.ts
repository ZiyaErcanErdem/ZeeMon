import { Component, OnInit, OnDestroy } from '@angular/core';
import { BasePageComponent } from 'app/pages/base-page.component';
import { GenericDynamicAction, QueryMode, Theme, Condition, Operator, PageViewMode, DynamicService } from 'angular-dynamic-page';
import { AccountService } from 'app/core/auth/account.service';

import { ActionScript } from 'app/shared/model/action-script.model';
import { ActionParam } from 'app/shared/model/action-param.model';

@Component({
  selector: 'jhi-page-action-script',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageActionScriptComponent extends BasePageComponent<ActionScript> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  constructor(dynamicService: DynamicService, accountService: AccountService) {
    super('ActionScript', dynamicService, accountService);
    this.setGridCols('id', 'scriptName', 'actionCode', 'scriptType', 'actionType', 'endpoint.endpointName');
    this.setCompactCols('id', 'scriptName');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(ActionScript)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 20;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        return config;
      })
      .withMetamodelConfiguration(cmd => {
        if (cmd.qualifier === 'ActionScript' || cmd.qualifier === 'Endpoint') {
          cmd.showWhenGrid = this.gridCols.includes(cmd.path);
          cmd.showWhenCompact = this.compactCols.includes(cmd.path);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }
      })
      .withRelationConfiguration(relation => {
        if (relation.qualifier === 'ActionScript') {
          relation.accessPath = 'action-scripts';
          relation.descriptionColumnName = 'scriptName';
        } else if (relation.qualifier === 'Endpoint') {
          relation.accessPath = 'endpoints';
          relation.descriptionColumnName = 'endpointName';
          relation.popupColumns = ['id', 'endpointName', 'endpointInstanceId'];
        } else if (relation.qualifier === 'ActionParam') {
          relation.accessPath = 'action-params';
          relation.descriptionColumnName = 'paramName';
          relation.popupColumns = ['id', 'paramName', 'paramValue'];

          this.pageManager!.createRelationPageManager(relation)
            .withGridColumns('id', 'paramName', 'paramDataType', 'paramValue')
            .withCompactColumns('id', 'paramName')
            .withSortingSamples(ActionParam);
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query.withCondition(Condition.OR).addPredicate('scriptName', Operator.LIKE, '');
      })
      .withViewer(PageViewMode.EDITOR);
  }

  ngDestroy(): void {
    this.registeredActions.forEach(a => this.pageManager!.unregisterAction(a));
    this.registeredActions = [];
    super.ngOnDestroy();
  }
}
