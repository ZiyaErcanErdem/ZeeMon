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

import { EndpointProperty } from 'app/shared/model/endpoint-property.model';
import { Endpoint } from 'app/shared/model/endpoint.model';

@Component({
  selector: 'jhi-page-endpoint-property',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageEndpointPropertyComponent extends BasePageComponent<EndpointProperty> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  constructor(dynamicService: DynamicService, accountService: AccountService) {
    super('EndpointProperty', dynamicService, accountService);
    this.setGridCols('id', 'propKey', 'propKeyType', 'propValue', 'propValueType', 'endpoint.endpointName');
    this.setCompactCols('id', 'propKey');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(Endpoint, EndpointProperty)
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
          cmd.showWhenGrid = this.gridCols.includes(cmd.path);
          cmd.showWhenCompact = this.compactCols.includes(cmd.path);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }
      })
      .withRelationConfiguration(relation => {
        if (relation.group === 'endpoint') {
          relation.accessPath = 'endpoints';
          relation.descriptionColumnName = 'endpointName';
          relation.popupColumns = ['id', 'endpointName'];
        } else if (relation.group === 'endpointProperty') {
          relation.accessPath = 'endpoint-properties';
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query
          .withCondition(Condition.OR)
          .addPredicate('endpoint.endpointName', Operator.LIKE, '')
          .and()
          .addPredicate('propKey', Operator.LIKE, '')
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
