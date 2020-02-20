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

import { Endpoint } from 'app/shared/model/endpoint.model';
import { EndpointProperty } from 'app/shared/model/endpoint-property.model';

@Component({
  selector: 'jhi-page-endpoint',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageEndpointComponent extends BasePageComponent<Endpoint> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  constructor(dynamicService: DynamicService, accountService: AccountService) {
    super('Endpoint', dynamicService, accountService);
    this.setGridCols('id', 'endpointName', 'endpointInstanceId', 'endpointType', 'endpointSpec');
    this.setCompactCols('id', 'endpointName');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(Endpoint)
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
        if (relation.group === 'endpointProperty') {
          relation.accessPath = 'endpoint-properties';
          relation.descriptionColumnName = 'propertyName';

          this.pageManager!.createRelationPageManager(relation)
            .withGridColumns('id', 'propKey', 'propKeyType', 'propValue', 'propValueType')
            .withCompactColumns('id', 'propKey')
            .withSortingSamples(EndpointProperty);
        } else if (relation.group === 'endpoint') {
          relation.accessPath = 'endpoints';
          relation.descriptionColumnName = 'endpointName';
        } else {
          if (relation.qualifier !== 'ActionScript' && relation.qualifier !== 'CheckScript') {
            relation.searchable = false;
          }
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query
          .withCondition(Condition.AND)
          .addPredicate('endpointName', Operator.LIKE, '')
          .and()
          .addPredicate('endpointInstanceId', Operator.LIKE, '')
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
