import { Component, OnInit, OnDestroy } from '@angular/core';
import { BasePageComponent } from 'app/pages/base-page.component';
import { GenericDynamicAction, QueryMode, Theme, Condition, Operator, PageViewMode, DynamicService } from 'angular-dynamic-page';
import { AccountService } from 'app/core/auth/account.service';

import { ContentMapper } from 'app/shared/model/content-mapper.model';
import { FieldMapping } from 'app/shared/model/field-mapping.model';

@Component({
  selector: 'jhi-page-content-mapper',
  template: `
    <zee-dynamic-page [manager]="pageManager"></zee-dynamic-page>
  `,
  styleUrls: []
})
export class PageContentMapperComponent extends BasePageComponent<ContentMapper> implements OnInit, OnDestroy {
  private registeredActions: Array<GenericDynamicAction<any>> = [];

  constructor(dynamicService: DynamicService, accountService: AccountService) {
    super('ContentMapper', dynamicService, accountService);
    this.setGridCols('id', 'mapperName', 'itemFormat', 'containsHeader', 'fieldDelimiter');
    this.setCompactCols('id', 'mapperName');
    this.theme = Theme.dark;
  }

  ngOnInit(): void {
    this.pageManager!.withSortingSample(ContentMapper)
      .withPageConfiguration(config => {
        config.queryMode = QueryMode.CRITERIA;
        config.itemsPerPage = 20;
        config.canDownloadExcel = this.canUserDownloadExcel();
        config.canUploadExcel = this.canUserUploadExcel();
        config.pageTheme = this.theme;
        return config;
      })
      .withMetamodelConfiguration(cmd => {
        if (cmd.qualifier === 'ContentMapper') {
          cmd.showWhenGrid = this.gridCols.includes(cmd.path);
          cmd.showWhenCompact = this.compactCols.includes(cmd.path);
        }
        if (cmd.name === 'id') {
          cmd.gridColWith = '80px';
          cmd.compactColWidth = '50px';
        }
      })
      .withRelationConfiguration(relation => {
        if (relation.group === 'contentMapper') {
          relation.accessPath = 'content-mappers';
        } else if (relation.qualifier === 'FieldMapping') {
          relation.accessPath = 'field-mappings';
          relation.descriptionColumnName = 'sourceName';
          relation.popupColumns = ['id', 'sourceName', 'targetName', 'targetColName'];

          this.pageManager!.createRelationPageManager(relation)
            .withGridColumns(
              'id',
              'sourceIndex',
              'sourceName',
              'sourceDataType',
              'targetName',
              'targetDataType',
              'targetColName',
              'requiredData'
            )
            .withCompactColumns('id', 'sourceName')
            .withSortingSamples(FieldMapping)
            .withRelationConfiguration((pb, rel) => {
              if (rel.qualifier === 'ContentMapper') {
                rel.accessPath = 'content-mappers';
                rel.descriptionColumnName = 'mapperName';
              }
              return rel;
            });
        }
        return relation;
      })
      .withDefaultQuery(query => {
        query
          .withCondition(Condition.OR)
          .addPredicate('mapperName', Operator.LIKE, '')
          .and()
          .addPredicate('fieldDelimiter', Operator.LIKE, '')
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
