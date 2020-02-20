import { Component, OnInit, OnDestroy } from '@angular/core';
import { BasePageComponent } from 'app/pages/base-page.component';
import { GenericDynamicAction, QueryMode, Theme, Condition, Operator, PageViewMode, DynamicService } from 'angular-dynamic-page';
import { AccountService } from 'app/core/auth/account.service';

import { FieldMapping } from 'app/shared/model/field-mapping.model';

@Component({
  selector: 'jhi-page-field-mapping',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageFieldMappingComponent extends BasePageComponent<FieldMapping> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  constructor(dynamicService: DynamicService, accountService: AccountService) {
    super('FieldMapping', dynamicService, accountService);
    this.setGridCols('id', 'sourceIndex', 'sourceName', 'sourceDataType', 'targetName', 'targetDataType', 'targetColName', 'requiredData');
    this.setCompactCols('id', 'sourceName');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(FieldMapping)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 20;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        return config;
      })
      .withMetamodelConfiguration(cmd => {
        if (cmd.qualifier === 'FieldMapping' || cmd.qualifier === 'ContentMapper') {
          cmd.showWhenGrid = this.gridCols.includes(cmd.path);
          cmd.showWhenCompact = this.compactCols.includes(cmd.path);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }
      })
      .withRelationConfiguration(relation => {
        if (relation.qualifier === 'FieldMapping') {
          relation.accessPath = 'field-mappings';
        } else if (relation.qualifier === 'ContentMapper') {
          relation.accessPath = 'content-mappers';
          relation.descriptionColumnName = 'mapperName';
          relation.popupColumns = ['id', 'mapperName', 'itemFormat'];
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query
          .withCondition(Condition.OR)
          .addPredicate('contentMapper.mapperName', Operator.LIKE, '')
          .and()
          .addPredicate('sourceName', Operator.LIKE, '')
          .and()
          .addPredicate('targetName', Operator.LIKE, '')
          .and()
          .addPredicate('targetColName', Operator.LIKE, '')
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
